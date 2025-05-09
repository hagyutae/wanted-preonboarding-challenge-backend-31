package com.wanted_preonboarding_challenge_backend.eCommerce.common;

import lombok.*;

import java.util.Map;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private boolean success;
    private ErrorDetail error;

    @Getter @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDetail {
        private String code;
        private String message;
        private Map<String, Object> details;
    }

    public static ErrorResponse of(String code, String message, Map<String, Object> details) {
        return ErrorResponse.builder()
                .success(false)
                .error(ErrorDetail.builder()
                        .code(code)
                        .message(message)
                        .details(details)
                        .build())
                .build();
    }
}
