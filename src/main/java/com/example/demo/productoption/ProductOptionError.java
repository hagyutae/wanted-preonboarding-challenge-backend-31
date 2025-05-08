package com.example.demo.productoption;

import com.example.demo.common.exception.BusinessError;
import lombok.Getter;

@Getter
public enum ProductOptionError implements BusinessError {
    PRODUCT_OPTION_ADD_FAIL("상품 옵션 추가에 실패하였습니다."),
    PRODUCT_OPTION_UPDATE_FAIL("상품 옵션 수정에 실패하였습니다.");

    private final String detailMessage;

    ProductOptionError(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
