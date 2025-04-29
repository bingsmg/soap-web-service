package com.sboss.hexing.server;

import com.sboss.hexing.server.service.CreditVendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import za.co.eskom.nrs.xmlvend.revenue._2_1.schema.CreditVendReq;
import za.co.eskom.nrs.xmlvend.revenue._2_1.schema.CreditVendResp;

/**
 * @author weibb
 */
@Endpoint
public class CreditVendEndpoint {

    private static final String NAMESPACE_URI = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema";

    private final CreditVendService creditVendService;

    @Autowired
    public CreditVendEndpoint(CreditVendService creditVendService) {
        this.creditVendService = creditVendService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "creditVendReq")
    @ResponsePayload
    public CreditVendResp handleCreditVendRequest(@RequestPayload CreditVendReq request) {
        return creditVendService.processCreditVendRequest(request);
    }
}
