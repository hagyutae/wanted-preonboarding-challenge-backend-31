package com.example.preonboarding.exception;

import com.example.preonboarding.response.error.ErrorCode;

import java.util.Map;

public class ProductRegisterException extends RuntimeException{
    private final ErrorCode errorCode;
    private final Map<String, Object> details;

    public ProductRegisterException(ErrorCode errorCode,Map<String, Object> details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = details;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
