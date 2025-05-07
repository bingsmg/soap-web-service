package com.sboss.hexing.consumer.service.impl;

import com.sboss.hexing.consumer.core.ServiceType;
import com.sboss.hexing.consumer.core.SoapBaseClient;
import com.sboss.hexing.consumer.core.SoapWebServiceTemplateFactory;
import com.sboss.hexing.consumer.creditvend.CreditVendResp;
import com.sboss.hexing.consumer.dto.revenue.CreditVendRequest;
import com.sboss.hexing.consumer.dto.revenue.CreditVendResponse;
import com.sboss.hexing.consumer.mapper.CreditVendMapper;
import com.sboss.hexing.consumer.service.VendService;
import jakarta.annotation.Resource;

/**
 * @author weibb
 */
//@Service
public class VendServiceImpl extends SoapBaseClient implements VendService {

    @Resource
    private CreditVendMapper creditVendMapper;

    public VendServiceImpl(SoapWebServiceTemplateFactory factory) {
        super(factory, ServiceType.VENDING);
    }

    @Override
    public CreditVendResponse creditVend(CreditVendRequest request) {
        CreditVendResp response = callWebService(creditVendMapper.toWsdlRequest(request), CreditVendResp.class);
        return creditVendMapper.fromWsdlResponse(response);
    }
}
