package com.wanted.mono.global.message;

import lombok.Getter;

@Getter
public enum MessageCode {
    PRODUCT_CREATE_SUCCESS("product.create.success"),
    PRODUCT_DELETE_FAIL("product.delete.fail"),
    PRODUCT_SEARCH_SUCCESS("product.search.success"),
    PRODUCT_DETAIL_SUCCESS("product.detail.success"),
    PRODUCT_UPDATE_SUCCESS("product.update.success"),
    PRODUCT_DELETE_SUCCESS("product.delete.success"),
    PRODUCT_OPTION_CREATE_SUCCESS("product.option.create.success"),
    PRODUCT_OPTION_DELETE_SUCCESS("product.option.delete.success");

    private final String code;

    MessageCode(String code) {
        this.code = code;
    }

}

