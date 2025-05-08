package com.sboss.hexing.producer.domain;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author weibb
 */
@Setter
@Getter
@XmlRootElement(name = "creditVendResp", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditVendResp", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
public class CreditVendResp {
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private ClientID clientID;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private ServerID serverID;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private TerminalID terminalID;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private ReqMsgID reqMsgID;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private String respDateTime;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private String dispHeader;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private ClientStatus clientStatus;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private Utility utility;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private Vendor vendor;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private CustVendDetail custVendDetail;
    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
    private CreditVendReceipt creditVendReceipt;

    @XmlType(name = "ClientID", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class ClientID {
        @XmlAttribute
        private String ean;
    }

    @XmlType(name = "ServerID", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class ServerID {
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

    @XmlType(name = "ReqMsgID", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class ReqMsgID {
        @XmlAttribute
        private String dateTime;
        @XmlAttribute
        private String uniqueNumber;
    }

    @XmlType(name = "ClientStatus", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class ClientStatus {
        @XmlElement
        private AvailCredit availCredit;

        @XmlType(name = "AvailCredit", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
        @Getter
        @Setter
        public static class AvailCredit {
            @XmlAttribute
            private String symbol;
            @XmlAttribute
            private String value;
        }
    }

    @XmlType(name = "Utility", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class Utility {
        @XmlAttribute
        private String address;
        @XmlAttribute
        private String name;
    }

    @XmlType(name = "Vendor", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class Vendor {
        @XmlAttribute
        private String address;
        @XmlAttribute
        private String name;
    }

    @XmlType(name = "CustVendDetail", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    @Getter
    @Setter
    public static class CustVendDetail {
        @XmlAttribute
        private String accNo;
        @XmlAttribute
        private String address;
        @XmlAttribute
        private String daysLastPurchase;
        @XmlAttribute
        private String locRef;
        @XmlAttribute
        private String name;
    }

    @XmlType(name = "CreditVendReceipt", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
    @Getter
    @Setter
    public static class CreditVendReceipt {
        @XmlAttribute
        private String receiptNo;
        @XmlElement
        private Transactions transactions;

        @XmlType(name = "Transactions", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
        @Getter
        @Setter
        public static class Transactions {
            @XmlElement(name = "tx")
            private List<Tx> txList;
            @XmlElement
            private Amount lessRound;
            @XmlElement
            private Amount tenderAmt;
            @XmlElement
            private Amount change;

            @XmlType(name = "Amount", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
            @Getter
            @Setter
            public static class Amount {
                @XmlAttribute
                private String symbol;
                @XmlAttribute
                private String value;
            }
        }
    }

    @XmlType(name = "Tx", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
    @XmlSeeAlso({CreditVendTx.class, ServiceChrgTx.class})
    @Getter
    @Setter
    public static abstract class Tx {
        @XmlAttribute
        private String receiptNo;
    }

    @XmlType(name = "CreditVendTx", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
    @Getter
    @Setter
    public static class CreditVendTx extends Tx {
        @XmlElement
        private Amount amt;
        @XmlElement
        private CreditTokenIssue creditTokenIssue;
        @XmlElement
        private Tariff tariff;

        @XmlType(name = "Amount", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
        @Getter
        @Setter
        public static class Amount {
            @XmlAttribute
            private String symbol;
            @XmlAttribute
            private String value;
        }

        @XmlType(name = "CreditTokenIssue", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
        @Getter
        @Setter
        public static class CreditTokenIssue {
            @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
            private String desc;
            @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
            private MeterDetail meterDetail;
            @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
            private Token token;
            @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
            private Units units;
            @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
            private Resource resource;

            @XmlType(name = "MeterDetail", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
            @Getter
            @Setter
            public static class MeterDetail {
                @XmlAttribute
                private String krn;
                @XmlAttribute
                private String msno;
                @XmlAttribute
                private String sgc;
                @XmlAttribute
                private String ti;
                @XmlElement
                private MeterType meterType;
                @XmlElement
                private String maxVendAmt;
                @XmlElement
                private String minVendAmt;
                @XmlElement
                private String maxVendEng;
                @XmlElement
                private String minVendEng;

                @XmlType(name = "MeterType", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
                @Getter
                @Setter
                public static class MeterType {
                    @XmlAttribute
                    private String at;
                    @XmlAttribute
                    private String tt;
                }
            }

            @XmlType(name = "Token", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
            @Getter
            @Setter
            public static class Token {
                @XmlElement
                private String stsCipher;
            }

            @XmlType(name = "Units", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
            @Getter
            @Setter
            public static class Units {
                @XmlAttribute
                private String siUnit;
                @XmlAttribute
                private String value;
            }

            @XmlType(name = "Resource", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
            @Getter
            @Setter
            public static class Resource {
                @XmlAttribute(name = "type", namespace = "http://www.w3.org/2001/XMLSchema-instance")
                private String type;
            }
        }

        @XmlType(name = "Tariff", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
        @Getter
        @Setter
        public static class Tariff {
            @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
            private String name;
        }
    }

    @XmlType(name = "ServiceChrgTx", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
    @Getter
    @Setter
    public static class ServiceChrgTx extends Tx {
        @XmlElement
        private Amount amt;
        @XmlElement
        private String accDesc;
        @XmlElement
        private String accNo;
        @XmlElement
        private Tariff tariff;

        @XmlType(name = "Amount", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
        @Getter
        @Setter
        public static class Amount {
            @XmlAttribute
            private String symbol;
            @XmlAttribute
            private String value;
        }

        @XmlType(name = "Tariff", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
        @Getter
        @Setter
        public static class Tariff {
            @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
            private String name;
        }
    }
}
