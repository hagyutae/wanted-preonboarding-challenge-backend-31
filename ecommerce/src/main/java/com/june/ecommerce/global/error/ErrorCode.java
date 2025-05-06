package com.june.ecommerce.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST.value(), "잘못된 입력 데이터"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "요청한 리소스를 찾을 수 없음"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(),"인증되지 않은 요청"),
    FORBIDDEN(HttpStatus.FORBIDDEN , HttpStatus.FORBIDDEN.value(), "권한이 없는 요청"),
    CONFLICT(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), "리소스 충돌 발생"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류");

    private final HttpStatus httpStatus;
    private final Integer statusCode;
    private final String message;
}
