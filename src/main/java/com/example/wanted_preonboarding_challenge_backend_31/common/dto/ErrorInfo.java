package com.example.wanted_preonboarding_challenge_backend_31.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.http.HttpStatus;

public record ErrorInfo<T>(
        String code,
        String message,
        @JsonIgnore HttpStatus httpCode,
        @JsonInclude(Include.NON_NULL) T details
) {

    private static <T> ErrorInfo<T> createErrorInfo(ErrorType errorType, T details, String customMessage) {
        return new ErrorInfo<>(
                errorType.getCode(),
                customMessage != null ? customMessage : errorType.getMessage(),
                errorType.getHttpStatus(),
                details
        );
    }

    public static <T> ErrorInfo<T> of(ErrorType errorType) {
        return ErrorInfo.createErrorInfo(errorType, null, null);
    }

    public static <T> ErrorInfo<T> of(ErrorType errorType, String customMessage) {
        return ErrorInfo.createErrorInfo(errorType, null, customMessage);
    }

    public static <T> ErrorInfo<T> ofWithDetails(ErrorType errorType, T details) {
        return ErrorInfo.createErrorInfo(errorType, details, null);
    }

    public static <T> ErrorInfo<T> ofWithDetails(ErrorType errorType, T details, String message) {
        return ErrorInfo.createErrorInfo(errorType, details, message);
    }
}
