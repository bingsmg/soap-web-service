package com.sboss.hexing.client.service;

import com.sboss.hexing.client.wsdl.CreditVendReq;
import com.sboss.hexing.client.wsdl.CreditVendResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * @author weibb
 */
@Service
public class VendClient {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public CreditVendResp creditVend(CreditVendReq request) {
        // request params process
        String VENDING_URI = "http://localhost:8080/ws";
        return (CreditVendResp) webServiceTemplate.marshalSendAndReceive(VENDING_URI,
                request, new SoapActionCallback(""));
    }
}
