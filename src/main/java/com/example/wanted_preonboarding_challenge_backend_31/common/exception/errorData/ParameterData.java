package com.example.wanted_preonboarding_challenge_backend_31.common.exception.errorData;

public record ParameterData(
        String key,
        String value,
        String reason
) {

    public static ParameterData of(String key, String value, String reason) {
        return new ParameterData(key, value, reason);
    }
}
