package com.shopping.mall.common.exception;

import com.shopping.mall.common.response.ErrorCode;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED, message);
    }
}