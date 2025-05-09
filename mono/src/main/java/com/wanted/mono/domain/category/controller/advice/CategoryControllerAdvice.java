package com.wanted.mono.domain.category.controller.advice;

import com.wanted.mono.global.common.Error;
import com.wanted.mono.global.common.ErrorCode;
import com.wanted.mono.global.common.ErrorResponse;
import com.wanted.mono.global.exception.CategoryNotFoundException;
import com.wanted.mono.global.exception.ProductEmptyException;
import com.wanted.mono.global.exception.ProductOptionGroupEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CategoryControllerAdvice {
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, Locale locale) {
        log.error("CategoryController 유효성 검사 실패 : ", ex);

        log.info("CategoryController 실패한 파라미터 추적");
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> details = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            details.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.info("CategoryController 에러 메시지 불러옴");
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;
        String localizedMessage = messageSource.getMessage(errorCode.getMessageKey(), null, locale);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .success(false)
                .error(Error.builder()
                        .code(ErrorCode.INVALID_INPUT.getCode())
                        .message(localizedMessage)
                        .details(details)
                        .build())
                .build();

        log.info("Bad Request 반환");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundResourceException(Exception ex, Locale locale) {
        log.error("CategoryController 조회 결과 없음 : ", ex);

        ErrorCode errorCode = ErrorCode.CATEGORY_EMPTY;

        String localizedMessage = messageSource.getMessage(errorCode.getMessageKey(), null, locale);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .success(false)
                .error(Error.builder()
                        .code(errorCode.getCode())
                        .message(localizedMessage)
                        .build())
                .build();

        log.info("Not Found 반환");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
