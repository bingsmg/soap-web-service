package com.sboss.hexing.client.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * @author weibb
 */
@Slf4j
public abstract class SoapBaseClient {

    private final WebServiceTemplate template;

    protected SoapBaseClient(SoapWebServiceTemplateFactory wsTemplateFactory, ServiceType serviceType) {
        this.template = wsTemplateFactory.create(serviceType);
    }

    protected <T> T callWebService(Object requestPayload, Class<T> responseClass) {
        return callWebService(requestPayload, responseClass, null);
    }

    protected <T> T callWebService(Object requestPayload, Class<T> responseClass, WebServiceMessageCallback requestCallback) {
        // 前置校验
        Assert.notNull(template, "WebServiceTemplate must not be null");
        Assert.notNull(requestPayload, "Request payload must not be null");
        Assert.notNull(responseClass, "Response class must not be null");

        log.debug("SOAP Request payload = {}", requestPayload);

        try {
            Object rawResponse = template.marshalSendAndReceive(requestPayload, requestCallback);
            if (rawResponse == null) {
                return null;
            }
            T result = responseClass.cast(rawResponse);
            log.debug("SOAP Response = {}", result);

            return result;

        } catch (Exception exception) {
            throw SoapClientExceptionHandler.handleException(exception);
        }
    }
}
