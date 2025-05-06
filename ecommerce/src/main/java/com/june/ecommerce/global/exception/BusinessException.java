package com.june.ecommerce.global.exception;

import com.june.ecommerce.global.error.ErrorCode;
import lombok.Getter;

import java.util.Map;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;
    private final Map<String, String> details;

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = message;
        this.details = null;
    }

    public BusinessException(ErrorCode errorCode, String message, String field, String detailMessage) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = message;
        this.details = Map.of(field, detailMessage);
    }

    public BusinessException(ErrorCode errorCode, String message, Map<String, String> details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }
}
