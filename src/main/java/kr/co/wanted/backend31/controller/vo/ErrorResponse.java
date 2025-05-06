package kr.co.wanted.backend31.controller.vo;

import java.util.Objects;

public record ErrorResponse(DetailError error, boolean success) {
    public ErrorResponse {
        if (success) {
            throw new IllegalArgumentException("success should be false");
        }
        Objects.requireNonNull(error);
    }
    public static ErrorResponse of(DetailError error) {
        return new ErrorResponse(error, false);
    }
}
