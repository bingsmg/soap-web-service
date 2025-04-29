package com.sboss.hexing.client.service;

import com.sboss.hexing.client.core.ServiceType;
import com.sboss.hexing.client.core.SoapBaseClient;
import com.sboss.hexing.client.core.SoapWebServiceTemplateFactory;
import com.sboss.hexing.client.creditvend.CreditVendResp;
import com.sboss.hexing.client.dto.request.CreditVendRequest;
import com.sboss.hexing.client.dto.response.CreditVendResponse;
import com.sboss.hexing.client.service.mapper.CreditVendMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author weibb
 */
@Service
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
