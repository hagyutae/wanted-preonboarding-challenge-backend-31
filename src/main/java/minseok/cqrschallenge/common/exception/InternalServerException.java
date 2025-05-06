package minseok.cqrschallenge.common.exception;

public class InternalServerException extends BaseException {

    public InternalServerException(String message) {
        super(ErrorCode.INTERNAL_ERROR, message);
    }
}