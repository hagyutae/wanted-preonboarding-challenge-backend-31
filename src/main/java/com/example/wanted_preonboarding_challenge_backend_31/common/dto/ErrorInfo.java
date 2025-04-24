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

    public static <T> ErrorInfo<T> error(ErrorType errorType, T details) {
        return new ErrorInfo<>(errorType.getCode(), errorType.getMessage(), errorType.getHttpStatus(), details);
    }

    public static <T> ErrorInfo<T> error(ErrorType errorType) {
        return ErrorInfo.error(errorType, null);
    }
}
