package kr.co.wanted.backend31.common.error;

import java.util.Map;

import lombok.ToString;

@ToString(callSuper = true)
public class InternalErrorException extends BaseException {
    
    public InternalErrorException() {
        super(ErrorCode.INTERNAL_ERROR);
    }

    public InternalErrorException(Map<String, Object> detail) {
        super(ErrorCode.INTERNAL_ERROR, detail);
    }

    public InternalErrorException(RuntimeException e) {
        super(ErrorCode.INTERNAL_ERROR, e);
    }

    public InternalErrorException(Map<String, Object> detail, RuntimeException e) {
        super(ErrorCode.INTERNAL_ERROR, detail, e);
    }
}
