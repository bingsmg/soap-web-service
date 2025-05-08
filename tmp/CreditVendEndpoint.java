package com.sboss.hexing.producer.endpoint;

import com.sboss.hexing.producer.domain.CreditVendReq;
import com.sboss.hexing.producer.domain.CreditVendResp;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * @author weibb
 */
@Endpoint
public class CreditVendEndpoint {
    private static final String NAMESPACE_URI = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "creditVendReq")
    @ResponsePayload
    public CreditVendResp handleCreditVendRequest(@RequestPayload CreditVendReq request) {
        // Mock implementation for demonstration
        CreditVendResp response = new CreditVendResp();

        // Populate response (simplified example)
        CreditVendResp.ClientID clientID = new CreditVendResp.ClientID();
        clientID.setEan(request.getClientID().getEan());
        response.setClientID(clientID);

        CreditVendResp.ServerID serverID = new CreditVendResp.ServerID();
        serverID.setEan("3210987654321");
        response.setServerID(serverID);

        CreditVendResp.TerminalID terminalID = new CreditVendResp.TerminalID();
        terminalID.setId(request.getTerminalID().getId());
        response.setTerminalID(terminalID);

        CreditVendResp.ReqMsgID reqMsgID = new CreditVendResp.ReqMsgID();
        reqMsgID.setDateTime(request.getMsgID().getDateTime());
        reqMsgID.setUniqueNumber(request.getMsgID().getUniqueNumber());
        response.setReqMsgID(reqMsgID);

        response.setRespDateTime("2023-06-08T16:58:22.000+08:00");
        response.setDispHeader("CREDIT VEND – TAX INVOICE|0|0|0");

        // Add more fields as needed (e.g., clientStatus, creditVendReceipt)
        // In a real implementation, call business logic or database

        return response;
    }
}
