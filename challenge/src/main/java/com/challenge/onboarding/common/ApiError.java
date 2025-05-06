package com.challenge.onboarding.common;

public class ApiError {
    private String code;
    private String message;
    private Object details;

    public ApiError(String code, String message, Object details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }
}
