package com.sboss.hexing.consumer.client;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author weibb
 */
@Slf4j
public class SoapClientExceptionHandler {

    private static final Map<String, String> ERROR_CODE_MAP = new HashMap<>();

    static {
        ERROR_CODE_MAP.put("VD.01010001", "用户不存在");
        ERROR_CODE_MAP.put("VD.01010004", "电表不存在");
        ERROR_CODE_MAP.put("VD.010032", "客户端ID未注册或被阻止");
        ERROR_CODE_MAP.put("VD.17020006", "代理余额不足");
        ERROR_CODE_MAP.put("VD.17020008", "系统正忙于处理订单");
    }

    public static RuntimeException handleException(Exception e) {
        if (e instanceof SoapFaultClientException) {
            return handleSoapFaultException((SoapFaultClientException) e);
        } else if (e instanceof WebServiceClientException) {
            return handleWebServiceClientException((WebServiceClientException) e);
        } else {
            return handleGenericException(e);
        }
    }

    private static RuntimeException handleSoapFaultException(SoapFaultClientException fault) {
        SoapFault soapFault = fault.getSoapFault();
        String faultString = fault.getFaultStringOrReason();
        log.error("SOAP Fault detected: faultString={}", faultString);

        SoapFaultDetail detail = soapFault.getFaultDetail();
        if (detail == null || !detail.getDetailEntries().hasNext()) {
            log.error("SOAP Fault detected: faultString={}", faultString);
            return new SoapClientException("SOAP Fault: " + faultString, fault);
        }

        try {
            Iterator<SoapFaultDetailElement> entries = detail.getDetailEntries();
            SoapFaultDetailElement detailElement = entries.next();
            Source source = detailElement.getSource();
            Element faultElement = sourceToElement(source);
            if (faultElement != null && faultElement.getLocalName().equals("xmlvendFaultResp")) {
                String clientID = getElementValue(faultElement, "clientID", "ean");
                String operatorMsg = getElementValue(faultElement, "operatorMsg");
                String custMsg = getElementValue(faultElement, "custMsg");
                String faultCode = getFaultCode(faultElement);
                String faultDesc = getFaultDesc(faultElement);

                // 确定错误消息，优先级：ERROR_CODE_MAP > custMsg > faultDesc > 默认消息
                String errorMessage = ERROR_CODE_MAP.get(faultCode);
                if (errorMessage == null) {
                    errorMessage = custMsg != null && !custMsg.isEmpty() ? custMsg :
                            faultDesc != null && !faultDesc.isEmpty() ? faultDesc : "未知SOAP故障";
                }

                // 记录详细日志
                String detailedMessage = String.format(
                        "SOAP Fault: faultCode=%s, errorMessage=%s, operatorMsg=%s, custMsg=%s, clientID=%s",
                        faultCode, errorMessage, operatorMsg, custMsg, clientID
                );
                log.error(detailedMessage, fault);

                // 分类抛出异常，优先匹配更具体的错误代码
                if (faultCode != null) {
                    if (faultCode.equals("VD.17020008")) {
                        return new RetryableException(errorMessage, faultCode, fault);
                    } else if (faultCode.startsWith("VD.0101")) {
                        return new UserMeterException(errorMessage, faultCode, fault);
                    } else if (faultCode.startsWith("VD.1702")) {
                        return new AgentException(errorMessage, faultCode, fault);
                    }
                }
                return new SoapClientException(errorMessage, faultCode, fault);
            }
        } catch (Exception ex) {
            log.error("Failed to parse SOAP Fault detail: {}", ex.getMessage(), ex);
        }

        log.error("SOAP Fault detected: faultString={}", faultString);
        return new SoapClientException("SOAP Fault: " + faultString, fault);
    }

    private static RuntimeException handleWebServiceClientException(WebServiceClientException wsEx) {
        String message = "WebService error: " + wsEx.getMessage();
        log.error(message, wsEx);
        return new SoapClientException(message, wsEx);
    }

    private static RuntimeException handleGenericException(Exception e) {
        String message = "Unexpected error: " + e.getMessage();
        log.error(message, e);
        return new SoapClientException(message, e);
    }

    private static String getElementValue(Element parentElement, String tagName, String attributeName) {
        NodeList nodes = parentElement.getElementsByTagNameNS("*", tagName);
        if (nodes.getLength() > 0) {
            Element element = (Element) nodes.item(0);
            if (attributeName != null) {
                return element.getAttribute(attributeName);
            }
            return element.getTextContent();
        }
        return null;
    }

    private static String getElementValue(Element parentElement, String tagName) {
        return getElementValue(parentElement, tagName, null);
    }

    private static String getFaultCode(Element parentElement) {
        NodeList faultNodes = parentElement.getElementsByTagNameNS("*", "faultCode");
        if (faultNodes.getLength() > 0) {
            return faultNodes.item(0).getTextContent();
        }
        return null;
    }

    private static String getFaultDesc(Element parentElement) {
        NodeList faultNodes = parentElement.getElementsByTagNameNS("*", "desc");
        if (faultNodes.getLength() > 0) {
            return faultNodes.item(0).getTextContent();
        }
        return null;
    }

    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();

    /**
     * 将 Source 转换为 org.w3c.dom.Element。
     *
     * @param source SOAP Fault Detail的Source对象
     * @return Element对象，或null如果转换失败
     */
    private static Element sourceToElement(Source source) {
        try {
            Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
            DOMResult domResult = new DOMResult();
            transformer.transform(source, domResult);
            return (Element) domResult.getNode().getFirstChild();
        } catch (Exception e) {
            log.error("Failed to convert Source to Element: {}", e.getMessage(), e);
            return null;
        }
    }

    @Getter
    public static class SoapClientException extends RuntimeException {
        private final String faultCode;

        public SoapClientException(String message, String faultCode, Throwable cause) {
            super(message, cause);
            this.faultCode = faultCode;
        }

        public SoapClientException(String message, Throwable cause) {
            this(message, null, cause);
        }

    }

    public static class UserMeterException extends SoapClientException {
        public UserMeterException(String message, String faultCode, Throwable cause) {
            super(message, faultCode, cause);
        }
    }

    public static class AgentException extends SoapClientException {
        public AgentException(String message, String faultCode, Throwable cause) {
            super(message, faultCode, cause);
        }
    }

    public static class RetryableException extends SoapClientException {
        public RetryableException(String message, String faultCode, Throwable cause) {
            super(message, faultCode, cause);
        }
    }
}
