package com.example.preonboarding.response.error;

public enum ErrorCode {
    INVALID_INPUT(400, "INVALID_INPUT", "잘못된 입력 데이터"),
    INTERNAL_ERROR(500, "INTERNAL_ERROR", "서버 내부 오류"),
    ;


    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
