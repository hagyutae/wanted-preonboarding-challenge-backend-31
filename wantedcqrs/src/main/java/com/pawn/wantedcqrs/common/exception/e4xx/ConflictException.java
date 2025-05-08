package com.pawn.wantedcqrs.common.exception.e4xx;

import com.pawn.wantedcqrs.common.exception.ResponseDefinition;
import com.pawn.wantedcqrs.common.exception.ResponseException;
import org.springframework.http.HttpStatus;

public enum ConflictException implements ResponseDefinition {

    CONFLICT(HttpStatus.CONFLICT, "CONFLICT", "리소스 충돌 발생"),
    PRODUCT_SLUG_DUP(HttpStatus.CONFLICT, "PRODUCT_SLUG_DUP", "이미 존재하는 Slug 입니다."),;

    private final ResponseException responseException;

    ConflictException(HttpStatus status, String code, String message) {
        this.responseException = new ResponseException(status, code, message);
    }

    @Override
    public ResponseException getResponseException() {
        return responseException;
    }

}