package com.preonboarding.exception;

import com.preonboarding.global.response.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    private ResponseEntity<BaseException> handleException(BaseException ex) {
        return ResponseEntity.ok(ex);
    }
}
