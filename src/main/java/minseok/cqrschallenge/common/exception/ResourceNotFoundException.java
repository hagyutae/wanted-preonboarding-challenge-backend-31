package minseok.cqrschallenge.common.exception;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message);
    }

    public ResourceNotFoundException(String message, Object details) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message, details);
    }
}