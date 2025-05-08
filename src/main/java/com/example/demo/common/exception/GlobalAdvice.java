package com.example.demo.common.exception;

import com.example.demo.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(GlobalException ex) {
        ApiResponse<Object> failResponse = ApiResponse.fail(ex.getCode(), ex.getMessage(), null);

        return ResponseEntity
                .status(HttpStatus.valueOf(ex.getStatusCode()))
                .body(failResponse);
    }
}