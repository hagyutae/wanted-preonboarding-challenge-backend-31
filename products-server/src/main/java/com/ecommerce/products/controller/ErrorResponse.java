package com.ecommerce.products.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private boolean success;
    private ErrorDetails error;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ErrorDetails {
        private String code;
        private String message;
        private Object details;
    }

    public static ErrorResponse of(String code, String message) {
        return ErrorResponse.builder()
                .success(false)
                .error(ErrorDetails.builder()
                        .code(code)
                        .message(message)
                        .build())
                .build();
    }

    public static ErrorResponse of(String code, String message, Object details) {
        return ErrorResponse.builder()
                .success(false)
                .error(ErrorDetails.builder()
                        .code(code)
                        .message(message)
                        .details(details)
                        .build())
                .build();
    }
}