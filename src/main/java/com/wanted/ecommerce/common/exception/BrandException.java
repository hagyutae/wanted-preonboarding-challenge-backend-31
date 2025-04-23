package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class BrandException extends BaseApiException {

    public BrandException(ErrorType errorType, String message) {
        super(errorType, message, null);
    }

    public BrandException(ErrorType errorType, String message, Map<String, String> details) {
        super(errorType, message, details);
    }
}
