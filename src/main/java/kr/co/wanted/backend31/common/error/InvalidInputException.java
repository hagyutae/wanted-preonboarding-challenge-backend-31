package kr.co.wanted.backend31.common.error;

import java.util.Map;

import lombok.ToString;

@ToString(callSuper = true)
public class InvalidInputException extends BaseException {

    public InvalidInputException() {
        super(ErrorCode.INVALID_INPUT);
    }

    public InvalidInputException(Map<String, Object> detail) {
        super(ErrorCode.INVALID_INPUT, detail);
    }

    public InvalidInputException(RuntimeException e) {
        super(ErrorCode.INVALID_INPUT, e);
    }

    public InvalidInputException(Map<String, Object> detail, RuntimeException e) {
        super(ErrorCode.INVALID_INPUT, detail, e);
    }
}
