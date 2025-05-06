package com.challenge.onboarding.common.handler.error;

import java.util.Map;

public record ErrorResponse(
        boolean success,
        Error error
) {
    public record Error(
            String code,
            String message,
            Map<String, Object> details
    ) {}

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(false, new Error(code, message, null));
    }

    public static ErrorResponse of(String code, String message, Map<String, Object> details) {
        return new ErrorResponse(false, new Error(code, message, details));
    }
}
