package com.sboss.hexing.client.controller;

import com.sboss.hexing.client.dto.request.CreditVendRequest;
import com.sboss.hexing.client.dto.response.CreditVendResponse;
import com.sboss.hexing.client.service.VendService;
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

    private final VendService vendService;

    @GetMapping("test")
    public CreditVendResponse vend() {
        CreditVendResponse resp = vendService.creditVend(new CreditVendRequest());
        log.debug(resp.toString());
        return resp;
    }
}
