package kr.co.wanted.backend31.common.error;

import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map<String, Object> detail;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        Objects.requireNonNull(errorCode);
        this.errorCode = errorCode;
        this.detail = null;
    }

    public BaseException(ErrorCode errorCode, Map<String, Object> detail) {
        super(errorCode.getMessage());
        Objects.requireNonNull(errorCode);
        Objects.requireNonNull(detail);
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public BaseException(ErrorCode errorCode, RuntimeException e) {
        super(errorCode.getMessage(), e);
        Objects.requireNonNull(errorCode);
        Objects.requireNonNull(e);
        this.errorCode = errorCode;
        this.detail = null;
    }

    public BaseException(ErrorCode errorCode, Map<String, Object> detail, RuntimeException e) {
        super(errorCode.getMessage(), e);
        Objects.requireNonNull(errorCode);
        Objects.requireNonNull(detail);
        Objects.requireNonNull(e);
        this.errorCode = errorCode;
        this.detail = detail;
    }
}
