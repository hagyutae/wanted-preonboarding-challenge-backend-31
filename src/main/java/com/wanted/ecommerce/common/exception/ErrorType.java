package com.wanted.ecommerce.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
public enum ErrorType {

    /**
     * 400 : 잘못된 입력 데이터
     */
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력 데이터"),
    /**
     * 404 : 요청한 리소스를 찾을 수 없음
     */
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없음"),
    /**
     * 401 : 인증되지 않은 요청
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 요청"),
    /**
     * 403 : 권한이 없는 요청
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없는 요청"),
    /**
     * 409 : 리소스 충돌 발생
     */
    CONFLICT(HttpStatus.CONFLICT, "리소스 충돌 발생"),
    /**
     * 500 : 서버 내부 오류
     */
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류");

    private final HttpStatusCode statusCode;
    private final String message;
}
