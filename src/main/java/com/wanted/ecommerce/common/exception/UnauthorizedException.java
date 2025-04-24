package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class UnauthorizedException extends BaseApiException {

    public UnauthorizedException(ErrorType errorType, String message) {
        this(errorType, message, null);
    }

    public UnauthorizedException(ErrorType errorType, String message, Map<String, String> details) {
        super(errorType, message, details);
    }
}
