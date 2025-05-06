package minseok.cqrschallenge.common.exception;

import lombok.Getter;

@Getter
public class ConflictException extends BaseException {

    private final String field;

    private final String value;

    public ConflictException(String message, String field, String value) {
        super(ErrorCode.CONFLICT, message);
        this.field = field;
        this.value = value;
    }

    public ConflictException(String message) {
        super(ErrorCode.CONFLICT, message);
        this.field = null;
        this.value = null;
    }
}