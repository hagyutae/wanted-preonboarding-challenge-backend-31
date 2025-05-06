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


    public ApiResponse(boolean success, T data, ErrorResponse error, String message) {
        this.success = success;
        this.data = data;
        this.error = error;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null, "요청이 성공적으로 처리되었습니다.");
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, null, message);
    }

    public static ApiResponse<?> error(ErrorResponse errorResponse) {
        return new ApiResponse<>(false, null, errorResponse, null);
    }
}