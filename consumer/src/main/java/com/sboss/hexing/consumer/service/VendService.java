package com.sboss.hexing.consumer.service;


import com.sboss.hexing.consumer.dto.revenue.CreditVendRequest;
import com.sboss.hexing.consumer.dto.revenue.CreditVendResponse;

/**
 * jersey
 *
 * @author weibb
 */
public interface VendService {
    CreditVendResponse creditVend(CreditVendRequest request);
}
