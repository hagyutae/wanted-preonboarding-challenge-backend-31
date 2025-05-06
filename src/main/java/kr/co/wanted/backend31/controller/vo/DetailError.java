package kr.co.wanted.backend31.controller.vo;

import java.util.Map;
import java.util.Objects;

import kr.co.wanted.backend31.common.error.BaseException;

public record DetailError(String code, String message, Map<String, Object> details) {
    public DetailError {
        Objects.requireNonNull(code);
        Objects.requireNonNull(message);
    }

    public static DetailError of(BaseException exception) {
        return new DetailError(exception.getErrorCode().getCode(), exception.getErrorCode().getMessage(),
                exception.getDetail());
    }
}
