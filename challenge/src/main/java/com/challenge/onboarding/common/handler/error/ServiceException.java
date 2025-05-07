package com.challenge.onboarding.common.handler.error;

public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;
    private final String details;

    public ServiceException(ErrorCode errorCode, String message, String details) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

    public ServiceException(ErrorCode errorCode, String details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.details = details;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
