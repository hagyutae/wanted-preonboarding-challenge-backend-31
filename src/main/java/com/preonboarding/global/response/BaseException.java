package com.preonboarding.global.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties({"stackTrace", "localizedMessage", "suppressed","cause","message"})
public class BaseException extends RuntimeException {
    private final boolean success;
    private final ErrorResponseDto error;

    public BaseException(boolean success,ErrorResponseDto error) {
        super(error.getMessage());
        this.success = success;
        this.error = error;
    }
}
