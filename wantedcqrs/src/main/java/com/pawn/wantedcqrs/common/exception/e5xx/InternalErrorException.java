package com.pawn.wantedcqrs.common.exception.e5xx;

import com.pawn.wantedcqrs.common.exception.ResponseDefinition;
import com.pawn.wantedcqrs.common.exception.ResponseException;
import org.springframework.http.HttpStatus;

public enum InternalErrorException implements ResponseDefinition {

    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "서버 에러로 인해 데이터를 로드 할 수 없습니다.");

    private final ResponseException responseException;

    InternalErrorException(HttpStatus status, String code, String message) {
        this.responseException = new ResponseException(status, code, message);
    }

    @Override
    public ResponseException getResponseException() {
        return responseException;
    }

}
