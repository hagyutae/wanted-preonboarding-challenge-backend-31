package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class ResourceNotFoundException extends BaseApiException{
    public ResourceNotFoundException(ErrorType errorType) {
        this(errorType, null);
    }

    public ResourceNotFoundException(ErrorType errorType, Map<String, String> details) {
        super(errorType, details);
    }
}
