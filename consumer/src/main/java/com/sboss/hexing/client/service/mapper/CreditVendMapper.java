package com.sboss.hexing.client.service.mapper;

import com.sboss.hexing.client.dto.request.CreditVendRequest;
import com.sboss.hexing.client.dto.response.CreditVendResponse;
import com.sboss.hexing.client.wsdl.CreditVendReq;
import com.sboss.hexing.client.wsdl.CreditVendResp;
import org.mapstruct.Mapper;

/**
 * @author weibb
 */
public class CreditVendMapper {
    public static CreditVendReq covert(CreditVendRequest requestType) {
        CreditVendReq request = new CreditVendReq();

        //...validate some fields


        return request;
    }
    public static CreditVendResponse covert(CreditVendResp responseType) {
        CreditVendResponse response = new CreditVendResponse();

        //...process some fields


        return response;
    }
}
//@Mapper(componentModel = "spring")
//public interface CreditVendMapper {
//    CreditVendReq toWsdlRequest(CreditVendRequest request);
//    CreditVendResponse fromWsdlResponse(CreditVendResp response);
//}