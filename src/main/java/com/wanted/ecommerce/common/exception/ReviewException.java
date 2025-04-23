package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class ReviewException extends BaseApiException{

    public ReviewException(ErrorType errorType, String message) {
        super(errorType, message, null);
    }

    public ReviewException(ErrorType errorType, String message, Map<String, String> details) {
        super(errorType, message, details);
    }
}
