package com.shopping.mall.common.exception;

import com.shopping.mall.common.response.ErrorCode;

public class BusinessException extends ApiException {
    public BusinessException(String message) {
        super(ErrorCode.INVALID_INPUT, message);
    }
}