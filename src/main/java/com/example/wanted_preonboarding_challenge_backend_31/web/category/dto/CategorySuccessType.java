package com.example.wanted_preonboarding_challenge_backend_31.web.category.dto;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.SuccessType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CategorySuccessType implements SuccessType {
    CATEGORY_SEARCH_PRODUCT("카테고리 상품 목록을 성공적으로 조회했습니다.");

    private final String message;

    @Override
    public String getMessage() {
        return this.message;
    }
}
