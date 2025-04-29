package com.sboss.hexing.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author weibb
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "hexing.webservice")
public class SoapServiceProperties {

    private String host;
    private int port;
    private String username;
    private String password;

    /**
     * 子服务配置
     */
    private Map<String, ServiceConfig> services;

    @Data
    public static class ServiceConfig {
        private String uri;
        private int connectTimeout;
        private int readTimeout;
    }
}