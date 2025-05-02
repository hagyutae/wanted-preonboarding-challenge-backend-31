package com.dino.cqrs_challenge.global.exception;

import com.dino.cqrs_challenge.global.response.ErrorCode;
import lombok.Getter;

import java.util.Map;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;
    private final Map<String, Object> details;

    public CustomException(ErrorCode errorCode, String message, Map<String, Object> details) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }
}
