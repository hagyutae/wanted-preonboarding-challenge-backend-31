package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.SuccessType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductSuccessType implements SuccessType {
    PRODUCT_CREATE("상품이 성공적으로 등록되었습니다.");

    private final String message;

    @Override
    public String getMessage() {
        return this.message;
    }
}
