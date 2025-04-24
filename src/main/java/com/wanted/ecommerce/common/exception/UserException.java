package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class UserException extends BaseApiException {

    public UserException(ErrorType errorType) {
        this(errorType, null);
    }

    public UserException(ErrorType errorType, Map<String, String> details) {
        super(errorType, errorType.getMessage(), details);
    }
}
