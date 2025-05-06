package com.shopping.mall.common.exception;

import com.shopping.mall.common.response.ErrorCode;

public class ConflictException extends ApiException {
    public ConflictException(String message) {
        super(ErrorCode.CONFLICT, message);
    }
}