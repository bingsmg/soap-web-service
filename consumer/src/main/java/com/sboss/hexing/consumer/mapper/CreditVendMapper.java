package com.sboss.hexing.consumer.mapper;

import com.sboss.hexing.consumer.creditvend.CreditVendReq;
import com.sboss.hexing.consumer.creditvend.CreditVendResp;
import com.sboss.hexing.consumer.dto.revenue.CreditVendRequest;
import com.sboss.hexing.consumer.dto.revenue.CreditVendResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author weibb
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditVendMapper {

    CreditVendReq covertRequest(CreditVendRequest requestType);

    CreditVendResponse covertResponse(CreditVendResp responseType);

    default CreditVendReq toWsdlRequest(CreditVendRequest requestType) {
        CreditVendReq request = covertRequest(requestType); // 先调用自动映射
        if (request != null) {
            // custom logic
        }
        return request;
    }

    default CreditVendResponse fromWsdlResponse(CreditVendResp responseType) {
        CreditVendResponse response = covertResponse(responseType); // 先调用自动映射
        if (response != null) { //
            // custom logic
        }
        return response;
    }
}