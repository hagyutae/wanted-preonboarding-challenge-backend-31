package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class InvalidInputException extends BaseApiException {

    public InvalidInputException(ErrorType errorType, String message) {
        this(errorType, message, null);
    }

    public InvalidInputException(ErrorType errorType, String message, Map<String, String> details) {
        super(errorType, message, details);
    }
}
