package com.wanted.mono.global.message;

import lombok.Getter;

@Getter
public enum MessageCode {
    PRODUCT_CREATE_SUCCESS("product.create.success"),
    PRODUCT_DELETE_FAIL("product.delete.fail"),
    PRODUCT_SEARCH_SUCCESS("product.search.success");

    private final String code;

    MessageCode(String code) {
        this.code = code;
    }

}

