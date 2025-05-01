package com.example.preonboarding.exception;

import com.example.preonboarding.response.error.ErrorCode;

import java.util.Map;

public class NotFoundResourceException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String message;
    private final Map<String, Object> details;

    public NotFoundResourceException(ErrorCode errorCode, String message, Map<String, Object> details) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }
}
