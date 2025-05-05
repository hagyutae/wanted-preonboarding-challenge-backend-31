package com.example.cqrsapp.common.exception;

import lombok.Getter;

@Getter
public class ConflictException extends BaseException {

    private final String field;
    private final String value;
    private final String detailMessage;

    public ConflictException(String field, String value, String detailMessages) {
        super(ErrorCode.CONFLICT);
        this.field = field;
        this.value = value;
        this.detailMessage = detailMessages;
    }

    public ConflictException(String field, String value, String detailMessage, Throwable cause) {
        super(ErrorCode.CONFLICT, cause);
        this.field = field;
        this.value = value;
        this.detailMessage = detailMessage;
    }
}
