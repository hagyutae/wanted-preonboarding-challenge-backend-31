package com.example.wanted_preonboarding_challenge_backend_31.common.dto;

public record FailRes<T>(
        boolean success,
        ErrorInfo<T> error
) {

    public static <T> FailRes<T> of(ErrorInfo<T> error) {
        return new FailRes<>(false, error);
    }
}
