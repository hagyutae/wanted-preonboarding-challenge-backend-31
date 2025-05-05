package minseok.cqrschallenge.common.exception;

public class ConflictException extends BaseException {
    public ConflictException(String message) {
        super(ErrorCode.CONFLICT, message);
    }

    public ConflictException(String message, Object details) {
        super(ErrorCode.CONFLICT, message, details);
    }
}