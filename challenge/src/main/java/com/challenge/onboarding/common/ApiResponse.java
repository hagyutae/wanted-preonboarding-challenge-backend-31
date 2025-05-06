package com.challenge.onboarding.common;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private ApiError error;

    public static <T> ApiResponse<T> success(T data, String message) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = true;
        res.data = data;
        res.message = message;
        return res;
    }

    public static ApiResponse<?> error(String code, String message, Object details) {
        ApiResponse<?> res = new ApiResponse<>();
        res.success = false;
        res.error = new ApiError(code, message, details);
        return res;
    }
}
