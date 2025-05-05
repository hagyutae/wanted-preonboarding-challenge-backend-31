package com.example.cqrsapp.common.exception;

import com.example.cqrsapp.common.response.APIErrorResponse;
import com.example.cqrsapp.common.response.APIErrorResponse.ConflictDetails;
import com.example.cqrsapp.common.response.APIErrorResponse.ResourceNotFoundDetails;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class CommonApiControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIErrorResponse<APIErrorResponse.BindingResultDetails>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("유효성 검증 실패: {}", e.getMessage());

        APIErrorResponse<APIErrorResponse.BindingResultDetails> response = APIErrorResponse.fail(ErrorCode.INVALID_INPUT, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 400 Bad Request - 잘못된 타입
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<APIErrorResponse<?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("잘못된 타입: {}", e.getMessage());
        APIErrorResponse<Void> response = APIErrorResponse.fail(ErrorCode.INVALID_INPUT);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 400 Bad Request - 바인딩 실패
    @ExceptionHandler(BindException.class)
    public ResponseEntity<APIErrorResponse<APIErrorResponse.BindingResultDetails>> handleBindException(BindException e) {
        log.error("바인딩 실패: {}", e.getMessage());

        APIErrorResponse<APIErrorResponse.BindingResultDetails> response = APIErrorResponse.fail(ErrorCode.INVALID_INPUT, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 404 Not Found - 비즈니스 로직에서 리소스를 찾지 못함
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIErrorResponse<ResourceNotFoundDetails>> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("리소스를 찾을 수 없음: {}", e.getMessage());

        ResourceNotFoundDetails details = ResourceNotFoundDetails.builder()
                .resourceId(e.getResourceId())
                .resourceType(e.getResourceType())
                .build();

        ErrorCode errorCode = ErrorCode.RESOURCE_NOT_FOUND;
        APIErrorResponse<ResourceNotFoundDetails> response = APIErrorResponse.fail(errorCode, details);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 401 Unauthorized - 인증 실패
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<APIErrorResponse<Void>> handleUnauthorizedException(UnauthorizedException e) {
        log.error("인증 실패: {}", e.getMessage());
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        APIErrorResponse<Void> response = APIErrorResponse.fail(errorCode);
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    // 403 Forbidden - 접근 권한 없음
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<APIErrorResponse<Void>> handleAccessDeniedException(ForbiddenException e) {
        log.error("접근 권한 없음: {}", e.getMessage());
        ErrorCode errorCode = ErrorCode.FORBIDDEN;
        APIErrorResponse<Void> response = APIErrorResponse.fail(errorCode);
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    // 409 Conflict - 리소스 충돌
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<APIErrorResponse<ConflictDetails>> handleResourceConflictException(ConflictException e) {
        log.error("리소스 충돌: {}", e.getMessage());
        ErrorCode conflict = ErrorCode.CONFLICT;
        ConflictDetails details = ConflictDetails.builder()
                .field(e.getField())
                .value(e.getValue())
                .detailMessage(e.getDetailMessage())
                .build();
        APIErrorResponse<ConflictDetails> response = APIErrorResponse.fail(conflict,details);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("예외 처리됨: " + e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorResponse<Void>> handleException(Exception e) {
        log.error("서버 내부 오류: ", e);
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        APIErrorResponse<Void> response = APIErrorResponse.fail(errorCode);
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @PostConstruct
    public void init() {
        log.info("✅ CommonApiControllerAdvice가 정상 등록되었습니다.");
    }
}
