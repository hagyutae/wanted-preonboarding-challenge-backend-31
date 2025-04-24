package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class ConflictException extends BaseApiException{

    public ConflictException(ErrorType errorType) {
        this(errorType, null);
    }

    public ConflictException(ErrorType errorType, Map<String, String> details) {
        super(errorType, details);
    }
}
