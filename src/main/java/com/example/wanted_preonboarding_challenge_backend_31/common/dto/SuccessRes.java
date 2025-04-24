package com.example.wanted_preonboarding_challenge_backend_31.common.dto;

public record SuccessRes<T>(
        boolean success,
        T data,
        String message
) {

    public static <T> SuccessRes<T> of(SuccessType successType, T data) {
        return new SuccessRes<>(true, data, successType.getMessage());
    }
}
