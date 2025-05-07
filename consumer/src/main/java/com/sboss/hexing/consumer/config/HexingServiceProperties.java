package com.sboss.hexing.consumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author weibb
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "hexing.vend")
public class HexingServiceProperties {
    private String protocol;
    private String uri;
    private String host;
    private int port;
    private String username;
    private String password;
    private String scanPackages;
    private int connectTimeout;
    private int readTimeout;
    private int maxConnections;

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