package com.sboss.hexing.server.domain;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author weibb
 */
@Setter
@Getter
@XmlRootElement(name = "creditVendReq", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditVendReq", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
public class CreditVendReq {
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private ClientID clientID;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private TerminalID terminalID;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private MsgID msgID;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private AuthCred authCred;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private Resource resource;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private IdMethod idMethod;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
    private PurchaseValue purchaseValue;

    @XmlType(name = "ClientID", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class ClientID {
        @XmlAttribute
        private String ean;
    }

    @XmlType(name = "TerminalID", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class TerminalID {
        @XmlAttribute
        private String id;
    }

    @XmlType(name = "MsgID", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class MsgID {
        @XmlAttribute
        private String dateTime;
        @XmlAttribute
        private String uniqueNumber;
    }

    @XmlType(name = "AuthCred", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class AuthCred {
        @XmlElement
        private String opName;
        @XmlElement
        private String password;
    }

    @XmlType(name = "Resource", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class Resource {
        @XmlAttribute(name = "type", namespace = "http://www.w3.org/2001/XMLSchema-instance")
        private String type;
    }

    @XmlType(name = "IdMethod", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class IdMethod {
        @XmlElement
        private MeterIdentifier meterIdentifier;
    }

    @XmlType(name = "MeterIdentifier", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class MeterIdentifier {
        @XmlAttribute
        private String msno;
    }

    @XmlType(name = "PurchaseValue", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
    @Getter
    @Setter
    public static class PurchaseValue {
        @XmlElement
        private Amt amt;
    }

    @XmlType(name = "Amt", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
    @Getter
    @Setter
    public static class Amt {
        @XmlAttribute
        private int value;
        @XmlAttribute
        private String symbol;
    }
}
