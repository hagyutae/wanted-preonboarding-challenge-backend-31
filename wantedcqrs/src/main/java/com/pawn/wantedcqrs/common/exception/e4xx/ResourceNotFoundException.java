package com.pawn.wantedcqrs.common.exception.e4xx;

import com.pawn.wantedcqrs.common.exception.ResponseDefinition;
import com.pawn.wantedcqrs.common.exception.ResponseException;
import org.springframework.http.HttpStatus;

public enum ResourceNotFoundException implements ResponseDefinition {

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", "요청한 리소스를 찾을 수 없음");

    private final ResponseException responseException;

    ResourceNotFoundException(HttpStatus status, String code, String message) {
        this.responseException = new ResponseException(status, code, message);
    }

    @Override
    public ResponseException getResponseException() {
        return responseException;
    }

}
