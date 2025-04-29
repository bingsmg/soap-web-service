package com.sboss.hexing.client.core;

import com.sboss.hexing.client.config.SoapServiceProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponents5MessageSender;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author weibb
 */
@Component
@RequiredArgsConstructor
public class SoapWebServiceTemplateFactory {

    private final Jaxb2Marshaller marshaller;
    private final SoapServiceProperties properties;

    // Cache templates keyed by service key
    private final Map<String, WebServiceTemplate> templateCache = new ConcurrentHashMap<>();

    /**
     * Optionally pre-initialize all templates at startup
     */
    @PostConstruct
    public void init() {
        // validate config
        if (CollectionUtils.isEmpty(properties.getServices())) {
            throw new IllegalArgumentException("No configuration found");
        }
    }

    /**
     * Returns a cached WebServiceTemplate for the given service, creating it if necessary.
     */
    public WebServiceTemplate create(ServiceType serviceType) {
        String key = serviceType.getKey();
        return templateCache.computeIfAbsent(key, k -> {
            SoapServiceProperties.ServiceConfig config = properties.getServices().get(k);
            if (config == null) {
                throw new IllegalArgumentException("No config found for service: " + serviceType);
            }
            return buildTemplate(config);
        });
    }

    private WebServiceTemplate buildTemplate(SoapServiceProperties.ServiceConfig config) {
        WebServiceTemplate template = new WebServiceTemplate(marshaller);
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        template.setDefaultUri(config.getUri());
        template.setMessageSender(new HttpComponents5MessageSender(createHttpClient(config)));
        return template;
    }

    private HttpClient createHttpClient(SoapServiceProperties.ServiceConfig config) {
        var requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(config.getConnectTimeout()))
                .setResponseTimeout(Timeout.ofMilliseconds(config.getReadTimeout()))
                .build();

        var credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(null, null, -1, null, null),
                new UsernamePasswordCredentials(
                        properties.getUsername(),
                        properties.getPassword().toCharArray()
                )
        );

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCredentialsProvider(credentialsProvider)
                .addRequestInterceptorFirst(
                        new HttpComponents5MessageSender.RemoveSoapHeadersInterceptor()
                )
                .build();
    }
}
