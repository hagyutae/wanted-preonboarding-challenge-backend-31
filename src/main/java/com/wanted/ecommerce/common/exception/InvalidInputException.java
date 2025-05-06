package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class InvalidInputException extends BaseApiException {

    public InvalidInputException(ErrorType errorType) {
        this(errorType, null);
    }

    public InvalidInputException(ErrorType errorType, Map<String, String> details) {
        super(errorType, details);
    }
}
