package com.challenge.onboarding.common.handler.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    INVALID_INPUT("INVALID_INPUT", "잘못된 입력 데이터", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "요청한 리소스를 찾을 수 없음", HttpStatus.NOT_FOUND),
    UNAUTHORIZED("UNAUTHORIZED", "인증되지 않은 요청", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("FORBIDDEN", "권한이 없는 요청", HttpStatus.FORBIDDEN),
    CONFLICT("CONFLICT", "리소스 충돌 발생", HttpStatus.CONFLICT),
    INTERNAL_ERROR("INTERNAL_ERROR", "서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

