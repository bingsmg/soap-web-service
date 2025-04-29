package com.sboss.hexing.client.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * @author weibb
 */
@Slf4j
public abstract class SoapBaseClient {

    private final WebServiceTemplate template;

    protected SoapBaseClient(SoapWebServiceTemplateFactory wsTemplateFactory, ServiceType serviceType) {
        this.template = wsTemplateFactory.create(serviceType);
    }

    /**
     * 简单版：发送SOAP请求，不传回调
     */
    protected <T> T callWebService(Object requestPayload, Class<T> responseClass) {
        return callWebService(requestPayload, responseClass, null);
    }

    /**
     * 高级版：发送SOAP请求，可以传自定义回调，比如加Header、认证信息
     */
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

        } catch (SoapFaultClientException fault) {
            log.error("SOAP Fault: {}", fault.getFaultStringOrReason(), fault);
            throw SoapClientExceptionHandler.handleException(fault);

        } catch (WebServiceClientException wsEx) {
            log.error("WebService error: {}", wsEx.getMessage(), wsEx);
            throw SoapClientExceptionHandler.handleException(wsEx);

        } catch (RuntimeException re) {
            log.error("Unexpected error: {}", re.getMessage(), re);
            throw re;
        }
    }
}
