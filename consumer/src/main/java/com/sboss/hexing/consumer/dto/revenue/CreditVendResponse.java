package com.sboss.hexing.consumer.dto.revenue;

import com.sboss.hexing.consumer.dto.base.MsgID;
import jakarta.xml.bind.annotation.*;
import lombok.Data;

/**
 * @author weibb
 */
@XmlRootElement(name = "creditVendResp", namespace = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CreditVendResponse {

    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private String clientID;

    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private String serverID;

    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private String terminalID;

    @XmlElement(name = "reqMsgID", namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private MsgID reqMsgID;

    @XmlElement(namespace = "http://www.nrs.eskom.co.za/xmlvend/base/2.1/schema")
    private String respDateTime;
}
