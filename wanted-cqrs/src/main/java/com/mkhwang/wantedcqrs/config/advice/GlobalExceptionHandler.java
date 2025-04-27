package com.mkhwang.wantedcqrs.config.advice;

import com.mkhwang.wantedcqrs.config.advice.dto.ApiResponse;
import com.mkhwang.wantedcqrs.config.exception.ForbiddenException;
import com.mkhwang.wantedcqrs.config.exception.ResourceConflictException;
import com.mkhwang.wantedcqrs.config.exception.ResourceNotFoundException;
import com.mkhwang.wantedcqrs.config.i18n.I18nService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final I18nService i18nService;

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ApiResponse<?>> handleForbiddenException(ForbiddenException ex) {
    return new ResponseEntity<>(
            ApiResponse.error("FORBIDDEN", "권한이 없는 요청"),
            HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> handleValidationExceptions(
          MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    List<Map<String, Object>> errorList = bindingResult.getFieldErrors().stream().map(error -> {
      Map<String, Object> errorMap = new HashMap<>();
//      String[] codes = error.getCodes();
//      errorMap.put("objectName", error.getObjectName());
//      errorMap.put("field", error.getField());
//      errorMap.put("rejectedValue", error.getRejectedValue());
//      errorMap.put("code", error.getCode());
//      errorMap.put("codes", codes);
//      errorMap.put("message", error.getDefaultMessage());
      errorMap.put(error.getField(), error.getDefaultMessage());
      return errorMap;
    }).toList();

    return new ResponseEntity<>(
            ApiResponse.error("INVALID_INPUT", "입력 데이터가 유효하지 않습니다.", errorList),
            HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
    return new ResponseEntity<>(
            ApiResponse.error("RESOURCE_NOT_FOUND", i18nService.getMessage(ex.getMessage()), ex.getDetails()),
            HttpStatus.CONFLICT);
  }


  @ExceptionHandler(ResourceConflictException.class)
  public ResponseEntity<ApiResponse<?>> handleResourceConflictException(ResourceConflictException ex) {
    return new ResponseEntity<>(
            ApiResponse.error("CONFLICT", "리소스 충돌이 발생했습니다.", ex.getDetails()),
            HttpStatus.CONFLICT);
  }


  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException ex) {
    ex.printStackTrace();
    return new ResponseEntity<>(
            ApiResponse.error("INTERNAL_ERROR", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."),
            HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
