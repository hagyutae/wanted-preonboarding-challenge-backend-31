package com.wanted.ecommerce.common.handler;

import com.wanted.ecommerce.common.exception.BaseApiException;
import com.wanted.ecommerce.common.response.ApiResponse;
import com.wanted.ecommerce.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.wanted.ecommerce")
@Order(value = 1)
public class ApiExceptionHandler {

    @ExceptionHandler(value = {BaseApiException.class})
    public ResponseEntity<ApiResponse<?>> apiExceptionAdvice(BaseApiException ex) {
        ApiResponse<ErrorResponse> response = ApiResponse.failure(
            ex.getStatusCode(),
            ex.getMessage(),
            ex.getDetails()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception e){
        ApiResponse<ErrorResponse> response = ApiResponse.failure(
            HttpStatus.INTERNAL_SERVER_ERROR,
            e.getMessage()
        );
        return ResponseEntity.internalServerError().body(response);
    }
}
