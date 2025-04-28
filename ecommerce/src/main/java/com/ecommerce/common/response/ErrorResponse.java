package com.ecommerce.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String code;
    private String message;
    private Object details;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.name();
        this.message = "에러 메시지";
    }

    public ErrorResponse(ErrorCode errorCode, Object details) {
        this.code = errorCode.name();
        this.message = "에러 메시지";
        this.details = details;
    }
}
