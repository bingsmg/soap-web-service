package com.sboss.hexing.consumer.controller;


import com.sboss.hexing.consumer.client.CreditVendClient;
import com.sboss.hexing.consumer.dto.revenue.CreditVendRequest;
import com.sboss.hexing.consumer.dto.revenue.CreditVendResponse;
import com.sboss.hexing.consumer.service.VendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weibb
 */
@Slf4j
@RestController
@RequestMapping("vend")
@RequiredArgsConstructor
public class VendController {

    private final CreditVendClient vendClient;

    @GetMapping("test")
    public CreditVendResponse vend() {
        CreditVendResponse resp = vendClient.creditVend(new CreditVendRequest());
        log.debug(resp.toString());
        return resp;
    }
}
