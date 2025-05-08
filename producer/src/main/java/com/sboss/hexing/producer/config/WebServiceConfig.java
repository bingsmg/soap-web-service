package com.sboss.hexing.producer.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*", "/ws");
    }

    @Bean
    public XsdSchemaCollection schemaCollection() {
        CommonsXsdSchemaCollection schemaCollection = new CommonsXsdSchemaCollection(
                new ClassPathResource("xsd/base.xsd"),
                new ClassPathResource("xsd/revenue.xsd")
        );
        schemaCollection.setInline(true);
        return schemaCollection;
    }


    @Bean(name = "creditvend")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchemaCollection schemaCollection) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CreditVendPort");
        wsdl11Definition.setLocationUri("/ws/vending");
        wsdl11Definition.setTargetNamespace("http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema");
        wsdl11Definition.setSchemaCollection(schemaCollection);
        return wsdl11Definition;
    }
} 