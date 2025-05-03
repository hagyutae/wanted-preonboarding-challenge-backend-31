package com.example.preonboarding.exception;

import com.example.preonboarding.response.error.ErrorCode;

import java.util.LinkedHashMap;
import java.util.Map;

public class BadRequestException extends RuntimeException{

    private final ErrorCode errorCode;
    private final Map<String, Object> details;

    public BadRequestException(ErrorCode errorCode,Map<String, Object> details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.details = new LinkedHashMap<>(details);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
