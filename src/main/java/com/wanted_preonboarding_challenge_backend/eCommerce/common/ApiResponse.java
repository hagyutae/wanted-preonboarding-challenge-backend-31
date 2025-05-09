package com.wanted_preonboarding_challenge_backend.eCommerce.common;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public static <T> ApiResponse<T> ok(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> fail(String errorMessage) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(errorMessage)
                .build();
    }
}
