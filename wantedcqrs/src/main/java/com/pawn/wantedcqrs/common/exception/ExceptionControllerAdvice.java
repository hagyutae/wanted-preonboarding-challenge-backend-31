package com.pawn.wantedcqrs.common.exception;

import com.pawn.wantedcqrs.common.dto.response.CommonApiResponse;
import com.pawn.wantedcqrs.common.exception.e4xx.InvalidUnputException;
import com.pawn.wantedcqrs.common.exception.e5xx.InternalErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonApiResponse<ResponseException>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Validation error: {}", exception.getMessage());

        ResponseException responseException = InvalidUnputException.INVALID_INPUT.getResponseException();

        return ResponseEntity
                .status(responseException.getStatus())
                .body(CommonApiResponse.error(responseException));
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<CommonApiResponse<ResponseException>> processException(ResponseException exception) {
        log.info("{}", exception);

        return ResponseEntity
                .status(exception.getStatus())
                .body(CommonApiResponse.error(exception));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse<ResponseException>> handleUnknownException(Exception exception) {
        log.info("{}", exception);

        ResponseException responseException = InternalErrorException.INTERNAL_ERROR.getResponseException();

        return ResponseEntity
                .status(responseException.getStatus())
                .body(CommonApiResponse.error(responseException));
    }

}