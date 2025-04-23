package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class CategoryException extends BaseApiException{
    public CategoryException(ErrorType errorType, String message) {
        super(errorType, message, null);
    }

    public CategoryException(ErrorType errorType, String message, Map<String, String> details) {
        super(errorType, message, details);
    }
}
