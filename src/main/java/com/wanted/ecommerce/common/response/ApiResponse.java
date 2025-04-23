package com.wanted.ecommerce.common.response;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String message;
    private ErrorResponse error;

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
            .success(true)
            .data(data)
            .message(message)
            .build();
    }

    public static <T> ApiResponse<T> failure(HttpStatusCode code, String message) {
        return failure(code, message, null);
    }

    public static <T> ApiResponse<T> failure(HttpStatusCode code, String message,
        Map<String, String> details) {
        return ApiResponse.<T>builder()
            .success(false)
            .error(ErrorResponse.builder()
                .code(String.valueOf(code.value()))
                .message(message)
                .details(details)
                .build())
            .build();
    }
}
