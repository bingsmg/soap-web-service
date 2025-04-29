package com.sboss.hexing.client;

import com.sboss.hexing.client.service.VendClient;
import com.sboss.hexing.client.wsdl.CreditVendReq;
import com.sboss.hexing.client.wsdl.CreditVendResp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HexingClientApplicationTests {

    @Autowired
    private VendClient vendClient;

    @Test
    void contextLoads() {


    }

    @Test
    void test1() {
        CreditVendResp creditVendResp = vendClient.creditVend(new CreditVendReq());
        System.out.println(creditVendResp);
    }

}
