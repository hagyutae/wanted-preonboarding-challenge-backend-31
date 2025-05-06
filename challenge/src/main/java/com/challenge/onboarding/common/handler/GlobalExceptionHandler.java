package com.challenge.onboarding.common.handler;

import com.challenge.onboarding.common.handler.error.ErrorCode;
import com.challenge.onboarding.common.handler.error.ErrorResponse;
import com.challenge.onboarding.common.handler.error.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(ServiceException e) {
        ErrorResponse errorResponse = ErrorResponse.of(
                e.getErrorCode().getCode(), e.getErrorCode().getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(
                errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

}
