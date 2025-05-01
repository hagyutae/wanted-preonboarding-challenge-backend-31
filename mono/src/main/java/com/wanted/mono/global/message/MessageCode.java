package com.wanted.mono.global.message;

import lombok.Getter;

@Getter
public enum MessageCode {
    PRODUCT_CREATE_SUCCESS("product.create.success"),
    PRODUCT_DELETE_FAIL("product.delete.fail");
    // ... 다른 메시지 키들도 여기에 추가

    private final String code;

    MessageCode(String code) {
        this.code = code;
    }

}

