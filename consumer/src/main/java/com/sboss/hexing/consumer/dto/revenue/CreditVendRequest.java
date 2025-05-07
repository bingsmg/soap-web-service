package com.sboss.hexing.consumer.dto.revenue;

import com.sboss.hexing.consumer.dto.base.AuthCred;
import com.sboss.hexing.consumer.dto.base.MsgID;
import jakarta.xml.bind.annotation.*;
import lombok.Data;

/**
 * @author weibb
 */
@XmlRootElement(name = "creditVendReq", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CreditVendRequest {

    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private String clientID;

    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private String terminalID;

    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private MsgID msgID;

    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private AuthCred authCred;

    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private String resource;

    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private IdMethod idMethod;

    @XmlElement(name = "purchaseValue")
    private PurchaseValue purchaseValue;

    @Data
    public static class IdMethod {
        private MeterIdentifier meterIdentifier;

        @Data
        public static class MeterIdentifier {
            @XmlAttribute(name = "msno")
            private String meterNumber;
        }
    }

    @Data
    public static class PurchaseValue {
        private Amount amt;

        @Data
        public static class Amount {
            @XmlAttribute
            private String value;
            @XmlAttribute
            private String symbol;
        }
    }
}
