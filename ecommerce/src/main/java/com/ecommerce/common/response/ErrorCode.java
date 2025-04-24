package com.ecommerce.common.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 400 Bad Request
    INVALID_INPUT(400, "잘못된 입력 데이터"),

    // 404 Not Found
    RESOURCE_NOT_FOUND(404, "요청한 리소스를 찾을 수 없음"),

    // 401 Unauthorized
    UNAUTHORIZED(401, "인증되지 않은 요청"),

    // 403 Forbidden
    FORBIDDEN(403, "권한이 없는 요청"),

    // 409 Conflict
    CONFLICT(409, "리소스 충돌 발생"),

    // 500 Server Error
    INTERNAL_ERROR(500, "서버 내부 오류");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
