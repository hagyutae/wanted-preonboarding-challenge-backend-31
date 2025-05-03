package com.example.preonboarding.response.error;

public enum ErrorCode {
    INVALID_INPUT(400, "INVALID_INPUT", "상품 등록에 실패했습니다."),
    RESOURCE_NOT_FOUND(404, "RESOURCE_NOT_FOUND", "요청한 상품을 찾을 수 없습니다."),
    INTERNAL_ERROR(500, "INTERNAL_ERROR", "서버 내부 오류"),
    DUPLICATE_PRODUCT(400, "DUPLICATE_PRODUCT", "상품 등록에 실패했습니다."),
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
