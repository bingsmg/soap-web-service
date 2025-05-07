package com.sboss.hexing.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weibb
 */
//@Configuration
public class SoapClientConfig {

    @Value("${hexing.webservice.packages}")
    private String[] wsPackages;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        // 使用配置的包列表
        marshaller.setPackagesToScan(wsPackages);

        // 配置其他JAXB属性
        Map<String, Object> props = new HashMap<>();
        marshaller.setMarshallerProperties(props);

        return marshaller;
    }
}
