package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class ProductException extends BaseApiException {

    public ProductException(ErrorType errorType, String message) {
        this(errorType, message, null);
    }

    public ProductException(ErrorType errorType, String message, Map<String, String> details) {
        super(errorType, message, details);
    }
}
