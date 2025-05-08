package com.example.demo.product;

import com.example.demo.common.exception.BusinessError;
import lombok.Getter;

@Getter
public enum ProductError implements BusinessError {
    PRODUCT_CREATE_FAIL("상품 등록에 실패하였습니다."),
    PRODUCT_FIND_FAIL("요청한 상품을 찾을 수 없습니다."),
    PRODUCT_UPDATE_FAIL("상품 수정에 실패하였습니다."),
    PRODUCT_DELETE_FAIL("상품 삭제에 실패하였습니다."),
    PRODUCT_IMAGE_ADD_FAIL("상품 이미지 등록에 실패하였습니다.");

    private final String detailMessage;

    ProductError(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
