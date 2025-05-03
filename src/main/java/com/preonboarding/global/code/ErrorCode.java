package com.preonboarding.global.code;

import lombok.Getter;

@Getter
public enum ErrorCode {//400
    INVALID_PRODUCT_BASE_PRICE_INPUT("INVALID_INPUT","잘못된 기본 가격 입력"),
    INVALID_PRODUCT_SALE_PRICE_INPUT("INVALID_INPUT","잘못된 판매 가격 입력"),
    INVALID_STOCK_INPUT("INVALID_INPUT","잘못된 재고 입력"),

    PRODUCT_NOT_FOUND("RESOURCE_NOT_FOUND","상품을 찾을 수 없습니다."),
    SELLER_NOT_FOUND("RESOURCE_NOT_FOUND","판매자를 찾을 수 없습니다."),
    BRAND_NOT_FOUND("RESOURCE_NOT_FOUND","브랜드를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND("RESOURCE_NOT_FOUND","카테고리를 잦을 수 없습니다."),
    TAG_NOT_FOUND("RESOURCE_NOT_FOUND","태그를 찾을 수 없습니다."),
    OPTION_NOT_FOUND("RESOURCE_NOT_FOUND","상품 옵션을 찾을 수 없습니다."),
    OPTION_GROUP_NOT_FOUND("RESOURCE_NOT_FOUND","옵션 그룹을 찾을 수 없습니다."),

    UNAUTHORIZED("UNAUTHORIZED","인증되지 않은 요청"), // 401

    FORBIDDEN("FORBIDDEN","권한이 없는 요청"), // 403

    CONFLICT("CONFLICT","리소스 충돌 발생"), // 409

    INTERNAL_ERROR("INTERNAL_ERROR","서버 내부 오류"); // 500

    private final String code;
    private final String message;

    ErrorCode(String code,String message) {
        this.code = code;
        this.message = message;
    }
}
