package minseok.cqrschallenge.common.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import minseok.cqrschallenge.common.dto.ApiResponse;
import minseok.cqrschallenge.common.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBaseException(BaseException ex) {
        log.error("BaseException: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .details(ex.getDetails())
                .build();
        
        return ResponseEntity
                .status(ex.getErrorCode().getStatus())
                .body(ApiResponse.error(errorResponse, ex.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationExceptions(Exception ex) {
        log.error("Validation Exception: {}", ex.getMessage(), ex);
        
        Map<String, String> validationErrors = new HashMap<>();
        
        if (ex instanceof MethodArgumentNotValidException validationEx) {
            validationEx.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                validationErrors.put(fieldName, errorMessage);
            });
        } else if (ex instanceof BindException bindEx) {
            bindEx.getBindingResult().getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                validationErrors.put(fieldName, errorMessage);
            });
        }
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.INVALID_INPUT.getCode())
                .message("입력 데이터가 유효하지 않습니다.")
                .details(validationErrors)
                .build();
        
        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getStatus())
                .body(ApiResponse.error(errorResponse, "입력 데이터가 유효하지 않습니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleException(Exception ex) {
        log.error("Unhandled Exception: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.INTERNAL_ERROR.getCode())
                .message("서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.")
                .build();
        
        return ResponseEntity
                .status(ErrorCode.INTERNAL_ERROR.getStatus())
                .body(ApiResponse.error(errorResponse, "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleConflictException(ConflictException ex) {
        Map<String, Object> details = new HashMap<>();

        if (ex.getField() != null) {
            details.put("field", ex.getField());
        }
        details.put("message", ex.getMessage());

        if (ex.getValue() != null) {
            details.put("value", ex.getValue());
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
            .code(ex.getErrorCode().getCode())
            .message("리소스 충돌이 발생했습니다.")
            .details(details)
            .build();

        return ResponseEntity
            .status(ex.getErrorCode().getStatus())
            .body(ApiResponse.error(errorResponse, ex.getMessage()));
    }

}