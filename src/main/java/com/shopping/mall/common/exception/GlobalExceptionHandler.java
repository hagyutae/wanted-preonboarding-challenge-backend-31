package com.shopping.mall.common.exception;

import com.shopping.mall.common.response.ApiResponse;
import com.shopping.mall.common.response.ErrorCode;
import com.shopping.mall.common.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 예외 (커스텀 예외 전체)
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(ApiResponse.error(errorResponse), getHttpStatus(e.getErrorCode()));
    }

    /**
     * Validation 예외
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<?> handleValidationException(Exception e) {
        Map<String, String> details = new HashMap<>();

        if (e instanceof MethodArgumentNotValidException exception) {
            exception.getBindingResult().getFieldErrors().forEach(error ->
                    details.put(error.getField(), error.getDefaultMessage()));
        } else if (e instanceof BindException exception) {
            exception.getBindingResult().getFieldErrors().forEach(error ->
                    details.put(error.getField(), error.getDefaultMessage()));
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.INVALID_INPUT.getCode())
                .message(ErrorCode.INVALID_INPUT.getMessage())
                .details(details)
                .build();

        return ResponseEntity.badRequest().body(ApiResponse.error(errorResponse));
    }

    /**
     * 그 외 예상하지 못한 에러
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {

        e.printStackTrace();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.INTERNAL_ERROR.getCode())
                .message(ErrorCode.INTERNAL_ERROR.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(errorResponse));
    }

    /**
     * 에러 코드 → HTTP Status 매핑
     */
    private HttpStatus getHttpStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            case INVALID_INPUT -> HttpStatus.BAD_REQUEST;
            case RESOURCE_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case CONFLICT -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}