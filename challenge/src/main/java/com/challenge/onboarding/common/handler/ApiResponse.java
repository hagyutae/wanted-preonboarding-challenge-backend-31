package com.challenge.onboarding.common.handler;

public record ApiResponse<T>(
        boolean success,
        T data,
        String message
) {
    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(true, data, message);
    }

    public static ApiResponse<Void> ok(String message) {
        return new ApiResponse<>(true, null, message);
    }
}
