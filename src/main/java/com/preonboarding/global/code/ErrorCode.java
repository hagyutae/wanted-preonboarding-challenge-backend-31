package com.preonboarding.global.code;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_INPUT("INVALID_INPUT","잘못된 입력 데이터"), //400
    NOT_FOUND("RESOURCE_NOT_FOUND","요청한 리소스를 찾을 없음"), //404
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
