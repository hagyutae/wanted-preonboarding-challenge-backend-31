package com.psh10066.commerce.api.common;

import com.psh10066.commerce.domain.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<ResourceNotFoundException.Details>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(
            ErrorResponse.of(e.getCode(), e.getMessage(), e.getDetails()),
            e.getCode().getHttpStatus()
        );
    }
}