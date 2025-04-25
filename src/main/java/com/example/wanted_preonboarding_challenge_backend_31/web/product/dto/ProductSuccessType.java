package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.SuccessType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductSuccessType implements SuccessType {
    PRODUCT_CREATE("상품이 성공적으로 등록되었습니다."),
    PRODUCT_UPDATE("상품이 성공적으로 수정되었습니다."),
    PRODUCT_DELETE("상품이 성공적으로 삭제되었습니다."),
    PRODUCT_OPTION_CREATE("상품 옵션이 성공적으로 추가되었습니다."),
    ;

    private final String message;

    @Override
    public String getMessage() {
        return this.message;
    }
}
