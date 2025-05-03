package wanted.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wanted.common.exception.code.ExceptionCode;
import wanted.common.exception.code.GlobalExceptionCode;
import wanted.common.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<?> handleCustomException(final CustomException e) {
        ExceptionCode error = e.getExceptionCode();
        log.error("[Custom Exception] {}", error.getMessage());
        return ResponseEntity.status(error.getStatus()).body(ErrorResponse.of(error.getCode(), error.getMessage(), error.getDetails()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(final Exception e) {
        log.error("[Exception] {}", e.getMessage());
        ExceptionCode error = GlobalExceptionCode.INTERNAL_ERROR;
        return ResponseEntity.status(error.getStatus()).body(ErrorResponse.of(error.getCode(), error.getMessage(), error.getDetails()));
    }
}