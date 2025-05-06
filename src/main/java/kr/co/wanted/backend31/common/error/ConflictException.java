package kr.co.wanted.backend31.common.error;

import java.util.Map;

import lombok.ToString;

@ToString(callSuper = true)
public class ConflictException extends BaseException {

    public ConflictException() {
        super(ErrorCode.CONFLICT);
    }

    public ConflictException(Map<String, Object> detail) {
        super(ErrorCode.CONFLICT, detail);
    }
    
    public ConflictException(RuntimeException e) {
        super(ErrorCode.CONFLICT, e);
    }

    public ConflictException(Map<String, Object> detail, RuntimeException e) {
        super(ErrorCode.CONFLICT, detail, e);
    }
}
