package com.sboss.hexing.client.core;

import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.soap.client.SoapFaultClientException;

/**
 * @author weibb
 */
public class SoapClientExceptionHandler {

    public static RuntimeException handleException(Exception e) {
        if (e instanceof WebServiceIOException) {
            return new RuntimeException("WebService IO 异常", e);
        } else if (e instanceof SoapFaultClientException) {
            return new RuntimeException("服务端返回Fault错误", e);
        } else {
            return new RuntimeException("未知WebService调用异常", e);
        }
    }
}
