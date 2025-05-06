package com.shopping.mall.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private ErrorResponse error;
    private String message;

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .build();
    }

    public static ApiResponse<?> error(ErrorResponse errorResponse) {
        return ApiResponse.builder()
                .success(false)
                .error(errorResponse)
                .build();
    }
}