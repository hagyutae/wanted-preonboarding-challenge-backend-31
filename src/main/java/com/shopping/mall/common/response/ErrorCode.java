package com.shopping.mall.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_INPUT("INVALID_INPUT", "잘못된 입력 데이터"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "요청한 리소스를 찾을 수 없음"),
    UNAUTHORIZED("UNAUTHORIZED", "인증되지 않은 요청"),
    FORBIDDEN("FORBIDDEN", "권한이 없는 요청"),
    CONFLICT("CONFLICT", "리소스 충돌 발생"),
    INTERNAL_ERROR("INTERNAL_ERROR", "서버 내부 오류");

    private final String code;
    private final String message;
}