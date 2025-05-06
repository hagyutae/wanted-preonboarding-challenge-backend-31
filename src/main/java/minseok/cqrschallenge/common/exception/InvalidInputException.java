package minseok.cqrschallenge.common.exception;

public class InvalidInputException extends BaseException {

    public InvalidInputException(String message) {
        super(ErrorCode.INVALID_INPUT, message);
    }

    public InvalidInputException(String message, Object details) {
        super(ErrorCode.INVALID_INPUT, message, details);
    }
}