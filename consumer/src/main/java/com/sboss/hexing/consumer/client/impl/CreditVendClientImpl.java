package com.sboss.hexing.consumer.client.impl;

import com.sboss.hexing.consumer.client.BaseWsClient;
import com.sboss.hexing.consumer.client.CreditVendClient;
import com.sboss.hexing.consumer.creditvend.CreditVendResp;
import com.sboss.hexing.consumer.dto.revenue.CreditVendRequest;
import com.sboss.hexing.consumer.dto.revenue.CreditVendResponse;
import com.sboss.hexing.consumer.mapper.CreditVendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author weibb
 */
@Component
@RequiredArgsConstructor
public class CreditVendClientImpl extends BaseWsClient implements CreditVendClient {

    private final CreditVendMapper creditVendMapper;

    // todo: Unified configuration
    // Example SOAP Action
    private static final String SOAP_ACTION_CREDIT_VEND = "http://www.nrs.eskom.co.za/xmlvend/revenue/2.1/schema/creditVend";

    @Override
    public CreditVendResponse creditVend(CreditVendRequest request) {
        CreditVendResp response = callService(creditVendMapper.toWsdlRequest(request),
                CreditVendResp.class, SOAP_ACTION_CREDIT_VEND);
        return creditVendMapper.fromWsdlResponse(response);
    }
}
