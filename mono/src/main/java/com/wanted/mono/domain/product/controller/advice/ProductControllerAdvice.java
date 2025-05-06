package com.wanted.mono.domain.product.controller.advice;

import com.wanted.mono.global.common.Error;
import com.wanted.mono.global.common.ErrorCode;
import com.wanted.mono.global.common.ErrorResponse;
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
public class ProductControllerAdvice {
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, Locale locale) {
        log.error("ProductController 유효성 검사 실패 : ", ex);

        log.info("ProductControllerAdvice 실패한 파라미터 추적");
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> details = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            details.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.info("ProductControllerAdvice 에러 메시지 불러옴");
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

    @ExceptionHandler(ProductEmptyException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundResourceException(Exception ex, Locale locale) {
        log.error("ProductController 조회 결과 없음 : ", ex);

        ErrorCode errorCode = ErrorCode.PRODUCT_EMPTY;

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, Locale locale) {
        log.error("중복 키 예외 발생", ex);

        ErrorCode errorCode = ErrorCode.CONFLICT;
        String localizedMessage = messageSource.getMessage(errorCode.getMessageKey(), null, locale);

        Map<String, String> details = new HashMap<>();
        String errorMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "";

        if (errorMessage.contains("slug")) {
            String duplicateValue = extractDuplicateValue(errorMessage);
            details.put("field", "slug");
            details.put("value", duplicateValue);
            details.put("message", "해당 슬러그는 이미 사용 중입니다.");
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .success(false)
                .error(Error.builder()
                        .code(errorCode.getCode())
                        .message(localizedMessage)
                        .details(details)
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(MethodArgumentTypeMismatchException ex, Locale locale) {
        log.error("파라미터 타입 불일치", ex);

        ErrorCode errorCode = ErrorCode.INVALID_TYPE_INPUT;
        String localizedMessage = messageSource.getMessage(errorCode.getMessageKey(), null, locale);

        Map<String, String> details = new HashMap<>();
        details.put(ex.getName(), ex.getValue() + "는 유효한 상품 ID가 아닙니다.");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .success(false)
                .error(Error.builder()
                        .code(errorCode.getCode())
                        .message(localizedMessage)
                        .details(details)
                        .build())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(ProductOptionGroupEmptyException.class)
    public ResponseEntity<ErrorResponse> handleProductOptionGroupException(Exception ex, Locale locale) {
        log.error("ProductController OptionGroup 조회 결과 없음 : ", ex);

        ErrorCode errorCode = ErrorCode.RESOURCE_NOT_FOUND;

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

    private String extractDuplicateValue(String raw) {
        Pattern pattern = Pattern.compile("\\(slug\\)=\\((.*?)\\)");
        Matcher matcher = pattern.matcher(raw);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
