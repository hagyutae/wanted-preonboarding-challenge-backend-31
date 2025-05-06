package kr.co.wanted.backend31.common.error;

import java.util.Map;

import lombok.ToString;

@ToString(callSuper = true)
public class UnauthorizedException extends BaseException {

    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }

    public UnauthorizedException(Map<String, Object> detail) {
        super(ErrorCode.UNAUTHORIZED, detail);
    }

    public UnauthorizedException(RuntimeException e) {
        super(ErrorCode.UNAUTHORIZED, e);
    }

    public UnauthorizedException(Map<String, Object> detail, RuntimeException e) {
        super(ErrorCode.UNAUTHORIZED, detail, e);
    }
}
