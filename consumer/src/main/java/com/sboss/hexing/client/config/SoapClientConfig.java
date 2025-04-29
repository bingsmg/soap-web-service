package com.sboss.hexing.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

/**
 * @author weibb
 */
@Configuration
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.sboss.hexing.client.wsdl");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setDefaultUri("http://xmlvend.eskom.co.za/soap");
        template.setUnmarshaller(marshaller);

        HttpComponentsMessageSender sender = new HttpComponentsMessageSender();
        sender.setConnectionTimeout(5000);
        sender.setReadTimeout(10000);

        // BasicAuth 添加
//        CredentialsProvider provider = new BasicCredentialsProvider();
//        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
//        provider.setCredentials(AuthScope.ANY, credentials);
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
//        sender.setHttpClient(httpClient);
//        template.setMessageSender(sender);

        return template;
    }
}
