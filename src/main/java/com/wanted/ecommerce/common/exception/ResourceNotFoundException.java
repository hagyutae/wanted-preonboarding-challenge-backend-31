package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class ResourceNotFoundException extends BaseApiException{
    public ResourceNotFoundException(ErrorType errorType, String message) {
        this(errorType, message, null);
    }

    public ResourceNotFoundException(ErrorType errorType, String message, Map<String, String> details) {
        super(errorType, message, details);
    }
}
