package com.wanted.ecommerce.common.exception;

import java.util.Map;

public class InternalServerException extends BaseApiException {

    public InternalServerException(ErrorType errorType) {
        this(errorType, null);
    }

    public InternalServerException(ErrorType errorType, Map<String, String> details) {
        super(errorType, details);
    }
}
