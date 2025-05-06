package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class ForbiddenException extends BaseApiException{

    public ForbiddenException(ErrorType errorType) {
        this(errorType, null);
    }

    public ForbiddenException(ErrorType errorType, Map<String, String> details) {
        super(errorType, details);
    }
}
