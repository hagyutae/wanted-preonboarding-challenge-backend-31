package com.shopping.mall.common.exception;

import com.shopping.mall.common.response.ErrorCode;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message);
    }
}