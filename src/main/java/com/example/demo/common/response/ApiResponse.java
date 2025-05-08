package com.example.demo.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiResponse<T>(
        Boolean success,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        ErrorResponse error
) {
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(
                true,
                data,
                message,
                null
        );
    }

    public static <Void> ApiResponse<Void> fail(String code, String message, Object details) {
        return new ApiResponse<>(
                false,
                null,
                null,
                new ErrorResponse(
                        code,
                        message,
                        details
                )
        );
    }
}
