package com.shopping.mall.common.exception;

import com.shopping.mall.common.response.ErrorCode;
import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {

    private final ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
