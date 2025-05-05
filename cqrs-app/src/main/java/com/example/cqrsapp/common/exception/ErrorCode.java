package com.example.cqrsapp.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT(HttpStatus.BAD_REQUEST,"C001","입력 데이터가 유효하지 않습니다."), // 장애 상황
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND,"C002","요청한 리소스를 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"C003","인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,"C004","해당 작업을 수행할 권한이 없습니다."),
    CONFLICT(HttpStatus.CONFLICT,"C005","리소스 충돌이 발생했습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C006","서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");

    private final HttpStatus status;
    private final String code;
    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}