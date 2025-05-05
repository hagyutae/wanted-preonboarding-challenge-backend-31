package com.example.cqrsapp.common.exception;

public class ForbiddenException extends BaseException{

    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN);
    }

    public ForbiddenException(Throwable cause) {
        super(ErrorCode.FORBIDDEN, cause);
    }
}
