package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class UserException extends BaseApiException {

    public UserException(ErrorType errorType, String message) {
        this(errorType, message, null);
    }

    public UserException(ErrorType errorType, String message, Map<String, String> details) {
        super(errorType, message, details);
    }
}
