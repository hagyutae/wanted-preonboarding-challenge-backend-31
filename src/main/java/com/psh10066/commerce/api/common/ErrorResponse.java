package com.psh10066.commerce.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ErrorResponse<T>(
    boolean success,
    ErrorDto<T> error
) {

    public static <T> ErrorResponse<T> of(ErrorCode errorCode, String message, T details) {
        return new ErrorResponse<>(
            false,
            new ErrorDto<>(
                errorCode.toString(),
                message,
                details
            )
        );
    }

    public record ErrorDto<T>(
        String code,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T details
    ) {
    }
}