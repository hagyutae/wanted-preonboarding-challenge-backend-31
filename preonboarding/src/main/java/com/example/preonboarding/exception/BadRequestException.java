package com.example.preonboarding.exception;

import com.example.preonboarding.response.error.ErrorCode;

import java.util.Map;

public class BadRequestException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String message;
    private final Map<String, Object> details;

    public BadRequestException(ErrorCode errorCode,Map<String, Object> details) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.details = details;
    }
}
