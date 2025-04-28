package com.ecommerce.common.exception;

public class ResourceConflictException extends RuntimeException {
    
    public ResourceConflictException(String message) {
        super(message);
    }
    
    public ResourceConflictException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("Resource '%s' already exists with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
