package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class InternalServerException extends BaseApiException {

    public InternalServerException(ErrorType errorType, String message) {
        this(errorType, message, null);
    }

    public InternalServerException(ErrorType errorType, String message, Map<String, String> details) {
        super(errorType, message, details);
    }
}
