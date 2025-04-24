package com.wanted.ecommerce.common.exception;

import java.util.Map;
import lombok.Getter;
import org.springframework.web.client.HttpStatusCodeException;

@Getter
public class BaseApiException extends HttpStatusCodeException {

    private final Map<String, String> details;

    public BaseApiException(ErrorType errorType, Map<String, String> details) {
        super(errorType.getStatusCode(), errorType.getMessage());
        this.details = details;
    }

    @Override
    public String getMessage() {
        return getStatusText();
    }

}
