package com.sboss.hexing.consumer.client;

import com.sboss.hexing.consumer.dto.revenue.CreditVendRequest;
import com.sboss.hexing.consumer.dto.revenue.CreditVendResponse;

/**
 * @author weibb
 */
public interface CreditVendClient {
    CreditVendResponse creditVend(CreditVendRequest request);
}
