package com.sboss.hexing.consumer.client;

import com.sboss.hexing.consumer.config.HexingServiceProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.transport.http.HttpComponents5MessageSender;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author weibb
 */
@Slf4j
@Component
public abstract class BaseWsClient extends WebServiceGatewaySupport {

    private HexingServiceProperties properties;

    @Autowired
    public void setProperties(HexingServiceProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        setDefaultUri(properties.getUri());

        var sender = createMessageSender();
        setMessageSender(sender);

        var marshaller = createMarshaller();
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        getWebServiceTemplate().setInterceptors(createInterceptors());

        doInit();
    }

    private ClientInterceptor[] createInterceptors() {
        return null;
    }

    private Jaxb2Marshaller createMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(properties.getScanPackages());
        Map<String, Object> props = new HashMap<>();
        marshaller.setMarshallerProperties(props);
        return marshaller;
    }

    private HttpComponents5MessageSender createMessageSender() {
        var credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(properties.getProtocol(), properties.getHost(), properties.getPort(),
                        null, null),
                new UsernamePasswordCredentials(
                        properties.getUsername(),
                        properties.getPassword().toCharArray()
                )
        );
        var requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(properties.getConnectTimeout(), TimeUnit.SECONDS)
                .setResponseTimeout(properties.getReadTimeout(), TimeUnit.SECONDS)
                .build();
        var connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(properties.getMaxConnections());

        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCredentialsProvider(credentialsProvider)
                .setConnectionManager(connectionManager)
                .addRequestInterceptorFirst(new HttpComponents5MessageSender.RemoveSoapHeadersInterceptor())
                .build();

        return new HttpComponents5MessageSender(httpClient);
    }

    protected void doInit() {
        // null
    }

    protected <T> T callService(Object request, Class<T> responseType) {
        return callService(request, responseType, null);
    }

    protected <T> T callService(Object request, Class<T> responseType,  WebServiceMessageCallback callback) {
        try {

            log.debug("Sending request to {}: {}", getDefaultUri(), request);
            long startTime = System.currentTimeMillis();

            @SuppressWarnings("unchecked")
            T response = (T) getWebServiceTemplate().marshalSendAndReceive(request, callback);

            long endTime = System.currentTimeMillis();
            log.debug("Received response in {}ms: {}", (endTime - startTime), response);

            return response;
        } catch (Exception e) {
            throw SoapClientExceptionHandler.handleException(e);
        }
    }
}
