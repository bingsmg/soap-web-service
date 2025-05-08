package com.sboss.hexing.producer.service;

import za.co.eskom.nrs.xmlvend.revenue._2_1.schema.CreditVendReq;
import za.co.eskom.nrs.xmlvend.revenue._2_1.schema.CreditVendResp;

/**
 * @author weibb
 */
public interface CreditVendService {
    CreditVendResp processCreditVendRequest(CreditVendReq request);

}
