package com.example.preonboarding.exception;

import com.example.preonboarding.response.error.ErrorCode;

import java.util.Map;

public class NotFoundResourceException extends RuntimeException{

    private final ErrorCode errorCode;
    private final Map<String, Object> details;

    public NotFoundResourceException(ErrorCode errorCode, String message, Map<String, Object> details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = details;
    }

    public NotFoundResourceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = null;
    }
}
