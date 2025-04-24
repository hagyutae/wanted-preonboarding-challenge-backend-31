package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class ConflicteEception extends BaseApiException{

    public ConflicteEception(ErrorType errorType, String message) {
        this(errorType, message, null);
    }

    public ConflicteEception(ErrorType errorType, String message, Map<String, String> details) {
        super(errorType, message, details);
    }
}
