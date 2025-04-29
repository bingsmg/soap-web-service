package com.sboss.hexing.client.service;

import com.sboss.hexing.client.dto.request.CreditVendRequest;
import com.sboss.hexing.client.dto.response.CreditVendResponse;
import com.sboss.hexing.client.wsdl.CreditVendReq;
import com.sboss.hexing.client.wsdl.CreditVendResp;

/**
 * @author weibb
 */
public interface VendService {
    CreditVendResponse creditVend(CreditVendRequest request);
}
