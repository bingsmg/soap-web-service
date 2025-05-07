package com.sboss.hexing.consumer.client.impl;

import com.sboss.hexing.consumer.client.BaseWsClient;
import com.sboss.hexing.consumer.client.CreditVendClient;
import com.sboss.hexing.consumer.config.HexingServiceProperties;
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

    @Override
    public CreditVendResponse creditVend(CreditVendRequest request) {
        CreditVendResp response = callService(creditVendMapper.toWsdlRequest(request), CreditVendResp.class);
        return creditVendMapper.fromWsdlResponse(response);
    }
}
