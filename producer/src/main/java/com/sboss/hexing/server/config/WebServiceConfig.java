package com.sboss.hexing.server.config;

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

//    @Bean(name = "creditvend")
//    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema revenueSchema) {
//        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
//        wsdl11Definition.setPortTypeName("CreditVendPort");
//        wsdl11Definition.setLocationUri("/ws");
//        wsdl11Definition.setTargetNamespace("http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema");
//        wsdl11Definition.setSchema(revenueSchema);
//        return wsdl11Definition;
//    }
//
//    @Bean
//    public XsdSchema revenueSchema() {
//        return new SimpleXsdSchema(new ClassPathResource("xsd/revenue.xsd"));
//    }

    @Bean
    public XsdSchemaCollection schemaCollection() {
        CommonsXsdSchemaCollection xsds = new CommonsXsdSchemaCollection(
                new ClassPathResource("xsd/base.xsd"),
                new ClassPathResource("xsd/revenue.xsd")
        );
        xsds.setInline(true);
        return xsds;
    }


    @Bean(name = "creditvend")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchemaCollection schemaCollection) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CreditVendPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema");
        wsdl11Definition.setSchemaCollection(schemaCollection);
        return wsdl11Definition;
    }
} 