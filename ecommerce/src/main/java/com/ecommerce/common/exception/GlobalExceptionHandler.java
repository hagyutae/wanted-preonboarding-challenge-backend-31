package com.ecommerce.common.exception;

import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.common.response.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 Bad Request - 유효성 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("유효성 검증 실패: {}", e.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ApiResponse<Map<String, String>> response = ApiResponse.error(ErrorCode.INVALID_INPUT, "유효성 검증에 실패했습니다", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 400 Bad Request - 잘못된 타입
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("잘못된 타입: {}", e.getMessage());
        ApiResponse<Void> response = ApiResponse.error(ErrorCode.INVALID_INPUT, "잘못된 요청 형식입니다");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    // 400 Bad Request - 바인딩 실패
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleBindException(BindException e) {
        log.error("바인딩 실패: {}", e.getMessage());
        
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ApiResponse<Map<String, String>> response = ApiResponse.error(ErrorCode.INVALID_INPUT, "요청 바인딩에 실패했습니다", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 404 Not Found - 비즈니스 로직에서 리소스를 찾지 못함
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("리소스를 찾을 수 없음: {}", e.getMessage());
        ApiResponse<Void> response = ApiResponse.error(ErrorCode.RESOURCE_NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    // 401 Unauthorized - 인증 실패
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(UnauthorizedException e) {
        log.error("인증 실패: {}", e.getMessage());
        ApiResponse<Void> response = ApiResponse.error(ErrorCode.UNAUTHORIZED, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    
    // 403 Forbidden - 접근 권한 없음
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException e) {
        log.error("접근 권한 없음: {}", e.getMessage());
        ApiResponse<Void> response = ApiResponse.error(ErrorCode.FORBIDDEN, "해당 리소스에 접근할 권한이 없습니다");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    
    // 409 Conflict - 리소스 충돌
    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceConflictException(ResourceConflictException e) {
        log.error("리소스 충돌: {}", e.getMessage());
        ApiResponse<Void> response = ApiResponse.error(ErrorCode.CONFLICT, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 500 Internal Server Error - 서버 내부 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("서버 내부 오류: ", e);
        ApiResponse<Void> response = ApiResponse.error(ErrorCode.INTERNAL_ERROR, "서버 내부 오류가 발생했습니다");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
