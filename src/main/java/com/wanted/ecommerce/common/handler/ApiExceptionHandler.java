package com.wanted.ecommerce.common.handler;

import com.wanted.ecommerce.common.exception.BaseApiException;
import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.response.ErrorDetailResponse;
import com.wanted.ecommerce.common.response.ErrorResponse;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.wanted.ecommerce")
@Order(value = 1)
public class ApiExceptionHandler {

    @ExceptionHandler(value = {BaseApiException.class})
    public ResponseEntity<ErrorResponse<ErrorDetailResponse>> apiExceptionAdvice(
        BaseApiException ex) {
        ErrorDetailResponse detail = ErrorDetailResponse.of(
            ex.getStatusCode(),
            ex.getMessage(),
            ex.getDetails()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(ErrorResponse.failure(detail));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse<ErrorDetailResponse>> handleValidException(
        MethodArgumentNotValidException e) {
        Map<String, String> fieldMap = e.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        ErrorDetailResponse detail = ErrorDetailResponse.of(ErrorType.INVALID_INPUT.getStatusCode(),
            ErrorType.INVALID_INPUT.getMessage(), fieldMap);
        return ResponseEntity.status(e.getStatusCode()).body(ErrorResponse.failure(detail));
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse<ErrorDetailResponse>> handleGenericException(Exception e) {
        ErrorDetailResponse detail = ErrorDetailResponse.of(
            HttpStatus.INTERNAL_SERVER_ERROR,
            e.getMessage(),
            null
        );
        return ResponseEntity.internalServerError().body(ErrorResponse.failure(detail));
    }
}
