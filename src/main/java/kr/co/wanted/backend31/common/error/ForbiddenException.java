package kr.co.wanted.backend31.common.error;

import java.util.Map;

import lombok.ToString;

@ToString(callSuper = true)
public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN);
    }
    
    public ForbiddenException(Map<String, Object> detail) {
        super(ErrorCode.FORBIDDEN, detail);
    }

    public ForbiddenException(RuntimeException e) {
        super(ErrorCode.FORBIDDEN, e);
    }
    
    public ForbiddenException(Map<String, Object> detail, RuntimeException e) {
        super(ErrorCode.FORBIDDEN, detail, e);
    }
}
