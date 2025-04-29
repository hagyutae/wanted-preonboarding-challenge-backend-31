package com.psh10066.commerce.api.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력 데이터"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없음"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 요청"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없는 요청"),
    CONFLICT(HttpStatus.CONFLICT, "리소스 충돌 발생"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류");

    private final HttpStatus httpStatus;
    private final String description;
}