package lkdcode.wanted.ecommerce.framework.common.api.advice;


import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.ConstraintViolationException;
import lkdcode.wanted.ecommerce.framework.common.api.response.ClientErrorResponse;
import lkdcode.wanted.ecommerce.framework.common.api.response.ServerErrorResponse;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiAdvice {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ClientErrorResponse<?>> handleBaseException(final ApplicationException e) {
        return ResponseEntity.status(e.getHttpStatus())
            .body(ClientErrorResponse.from(
                e.getCode(),
                e.getDescription()
            ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ClientErrorResponse<?>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final var message = new HashMap<String, String>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
                final var field = error.getField();
                final var defaultMessage = error.getDefaultMessage();
                log.info("[   MethodArgumentNotValidException   ] field: {}, rejected value: {}, message: {}",
                    field, error.getRejectedValue(), defaultMessage);

                message.put(field, defaultMessage);
            }
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ClientErrorResponse.invalidInput(message));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ClientErrorResponse<?>> handleConstraintViolationException(final ConstraintViolationException e) {
        final var message = new HashMap<String, String>();

        e.getConstraintViolations().forEach(violation -> {
                final var invalidValue = violation.getInvalidValue();
                final var defaultMessage = violation.getMessage();
                log.info("[   ConstraintViolationException    ] Property: {}, Invalid value: {}, Message: {}",
                    violation.getPropertyPath(), invalidValue, defaultMessage);

                message.put(invalidValue.toString(), defaultMessage);
            }
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ClientErrorResponse.invalidInput(message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ClientErrorResponse<?>> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.info("[   HttpMessageNotReadableException   ] cause: {}, message: {}",
            e.getCause() != null ? e.getCause().getMessage() : "N/A", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ClientErrorResponse.invalidInput("HTTP 요청이 올바르지 않습니다."));
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ClientErrorResponse<?>> handleJsonMappingException(final JsonMappingException e) {
        log.info("[   JsonMappingException   ] Path: {}, Message: {}",
            e.getPathReference(), e.getOriginalMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ClientErrorResponse.invalidInput("JSON 형식이 올바르지 않습니다."));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ClientErrorResponse<?>> handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        log.info("[   MissingRequestHeaderException   ] missing header: {}, message: {}",
            e.getHeaderName(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ClientErrorResponse.invalidInput("HTTP 요청 헤더가 올바르지 않습니다."));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ServerErrorResponse<?>> handleSQLException(final SQLException e) {
        log.warn("[   SQLException   ] SQLState: {}, ErrorCode: {}, message: {}",
            e.getSQLState(), e.getErrorCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ServerErrorResponse.from("알 수 없는 에러 발생."));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ServerErrorResponse<?>> handleDataAccessException(final DataAccessException e) {
        log.warn("[   DataAccessException   ] message: {}", e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ServerErrorResponse.from("데이터 베이스 에러 발생"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerErrorResponse<?>> handleException(final Exception e) {
        log.error("[   Exception   ] localizedMessage: {}, message: {}", e.getLocalizedMessage(), e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ServerErrorResponse.from("알 수 없는 에러 발생."));
    }
}