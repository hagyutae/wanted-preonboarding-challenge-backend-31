package com.june.ecommerce.global.error;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

public class ErrorResponse {
    private boolean success = false;
    private ErrorDetail error;

    public ErrorResponse(ErrorDetail errorDetail) {
        this.error = errorDetail;
    }

    @Getter
    public static class ErrorDetail {
        private String code;
        private String message;
        private Map<String, String> details; // optional

        public ErrorDetail(String code, String message, Map<String, String> details) {
            this.code = code;
            this.message = message;
            this.details = details;
        }
    }


    public static ErrorResponse of(ErrorCode errorCode, Map<String, String> details) {
        return new ErrorResponse(
                new ErrorDetail(errorCode.name(), errorCode.getMessage(), details)
        );
    }
}