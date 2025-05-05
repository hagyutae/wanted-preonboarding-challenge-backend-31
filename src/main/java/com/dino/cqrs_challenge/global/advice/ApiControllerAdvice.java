package com.dino.cqrs_challenge.global.advice;

import com.dino.cqrs_challenge.global.exception.CustomException;
import com.dino.cqrs_challenge.global.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.dino.cqrs_challenge")
public class ApiControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ApiErrorResponse handleCustomException(CustomException e) {
        return ApiErrorResponse.builder()
                .success(false)
                .error(ApiErrorResponse.ErrorDetail.builder()
                        .code(e.getErrorCode())
                        .message(e.getMessage())
                        .details(e.getDetails())
                        .build())
                .build();
    }

}
