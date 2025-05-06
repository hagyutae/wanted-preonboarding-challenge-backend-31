package com.pawn.wantedcqrs.common.exception.e4xx;

import com.pawn.wantedcqrs.common.exception.ResponseDefinition;
import com.pawn.wantedcqrs.common.exception.ResponseException;
import org.springframework.http.HttpStatus;

public enum ForbiddenException implements ResponseDefinition {

    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "권한이 없는 요청");

    private final ResponseException responseException;

    ForbiddenException(HttpStatus status, String code, String message) {
        this.responseException = new ResponseException(status, code, message);
    }

    @Override
    public ResponseException getResponseException() {
        return responseException;
    }

}