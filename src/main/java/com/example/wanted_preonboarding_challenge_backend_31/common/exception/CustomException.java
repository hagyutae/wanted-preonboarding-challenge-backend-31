package com.example.wanted_preonboarding_challenge_backend_31.common.exception;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.ErrorInfo;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private Exception originalException;
    private final ErrorInfo<?> errorInfo;

    public CustomException(ErrorInfo<?> errorInfo) {
        this.errorInfo = errorInfo;
    }

    public CustomException(ErrorInfo<?> errorInfo, Exception originalException) {
        this.errorInfo = errorInfo;
        this.originalException = originalException;
    }
}
