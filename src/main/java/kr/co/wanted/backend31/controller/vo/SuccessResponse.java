package kr.co.wanted.backend31.controller.vo;

import java.util.Objects;
import java.util.Optional;

public record SuccessResponse<T>(T data, String message, boolean success) {
    public SuccessResponse {
        if (!success) {
            throw new IllegalArgumentException("success should be true");
        }
        Objects.requireNonNull(message);
    }

    public static <T> SuccessResponse<T> of(Optional<T> data) {
        return new SuccessResponse<T>(data.orElseGet(() -> null), "요청이 성공적으로 처리되었습니다.", true);
    }
}
