package com.preonboarding.global.response;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private boolean success;
    private ErrorResponseDto error;
}
