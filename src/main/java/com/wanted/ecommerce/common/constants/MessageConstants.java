package com.wanted.ecommerce.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageConstants {
    CREATED_PRODUCTS("상품이 성공적으로 등록되었습니다."),
    FUNDED_ALL_PRODUCTS("상품 목록을 성공적으로 조회했습니다."),
    FUNDED_PRODUCT_DETAIL("상품 상세 정보를 성공적으로 조회했습니다."),
    UPDATED_PRODUCT("상품이 성공적으로 수정되었습니다.");

    private final String message;
}
