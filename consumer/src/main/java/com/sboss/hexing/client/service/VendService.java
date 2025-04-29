package com.sboss.hexing.client.service;

import com.sboss.hexing.client.dto.request.CreditVendRequest;
import com.sboss.hexing.client.dto.response.CreditVendResponse;

/**
 * @author weibb
 */
public interface VendService {
    CreditVendResponse creditVend(CreditVendRequest request);
}
