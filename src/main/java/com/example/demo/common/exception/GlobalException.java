package com.example.demo.common.exception;

public class GlobalException extends RuntimeException{
    private final ErrorCode errorCode;
    private final BusinessError businessError;

    public GlobalException(ErrorCode errorCode, BusinessError businessError) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.businessError = businessError;
    }

    public String getCode() {
        return errorCode.name();
    }

    public Integer getStatusCode() {
        return errorCode.getStatus();
    }

    @Override
    public String getMessage() {
        return businessError.getDetailMessage();
    }
}
