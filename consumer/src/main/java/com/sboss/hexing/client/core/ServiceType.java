package com.sboss.hexing.client.core;

import lombok.Getter;

/**
 * @author weibb
 */
@Getter
public enum ServiceType {

    VENDING("vending"),
    REISSUE("reissue"),
    ADVICE("advice");

    private final String key;

    ServiceType(String key) {
        this.key = key;
    }

}
