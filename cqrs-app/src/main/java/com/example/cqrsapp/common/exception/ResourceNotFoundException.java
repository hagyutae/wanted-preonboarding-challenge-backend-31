package com.example.cqrsapp.common.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends BaseException{
    private final ErrorCode errorCode;
    private final String resourceType;
    private final String resourceId;

    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(ErrorCode.RESOURCE_NOT_FOUND);
        this.errorCode = ErrorCode.RESOURCE_NOT_FOUND;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public ResourceNotFoundException(String resourceType, String resourceId, Throwable cause) {
        super(ErrorCode.RESOURCE_NOT_FOUND, cause);
        this.errorCode = ErrorCode.RESOURCE_NOT_FOUND;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }
}
