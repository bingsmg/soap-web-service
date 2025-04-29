package com.sboss.hexing.client.service;

import com.sboss.hexing.client.core.ServiceType;
import com.sboss.hexing.client.core.SoapBaseClient;
import com.sboss.hexing.client.core.SoapWebServiceTemplateFactory;
import com.sboss.hexing.client.dto.request.CreditVendRequest;
import com.sboss.hexing.client.dto.response.CreditVendResponse;
import com.sboss.hexing.client.service.mapper.CreditVendMapper;
import com.sboss.hexing.client.wsdl.CreditVendReq;
import com.sboss.hexing.client.wsdl.CreditVendResp;
import org.springframework.stereotype.Service;

/**
 * @author weibb
 */
@Service
public class VendServiceImpl extends SoapBaseClient implements VendService {

    public VendServiceImpl(SoapWebServiceTemplateFactory factory) {
        super(factory, ServiceType.VENDING);
    }

    @Override
    public CreditVendResponse creditVend(CreditVendRequest request) {
        CreditVendReq req = CreditVendMapper.covert(request);
        CreditVendResp response = callWebService(req, CreditVendResp.class);
        return CreditVendMapper.covert(response);
    }
}
