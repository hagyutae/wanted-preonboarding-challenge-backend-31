package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class UnauthorizedException extends BaseApiException {

    public UnauthorizedException(ErrorType errorType) {
        this(errorType, null);
    }

    public UnauthorizedException(ErrorType errorType, Map<String, String> details) {
        super(errorType, details);
    }
}
