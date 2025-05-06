package com.shopping.mall.common.exception;

import com.shopping.mall.common.response.ErrorCode;

public class ForbiddenException extends ApiException {
    public ForbiddenException(String message) {
        super(ErrorCode.FORBIDDEN, message);
    }
}