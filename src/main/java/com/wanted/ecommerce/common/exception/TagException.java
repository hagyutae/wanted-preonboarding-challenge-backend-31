package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class TagException extends BaseApiException {

    public TagException(ErrorType errorType, String message) {
        super(errorType, message, null);
    }

    public TagException(ErrorType errorType, String message, Map<String, String> details) {
        super(errorType, message, details);
    }
}
