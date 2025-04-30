package com.example.wanted_preonboarding_challenge_backend_31.common.exception;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.ErrorType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum CommonErrorType implements ErrorType {

    INVALID_INPUT("입력 데이터가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    API_NOT_FOUND("존재하지 않는 API입니다.", HttpStatus.NOT_FOUND),
    UNAUTHORIZED("인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("해당 작업을 수행할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    CONFLICT("리소스 충돌이 발생했습니다.", HttpStatus.CONFLICT),
    PARAMETER_VALIDATION_ERROR("파라미터 검증에 실패했습니다.", HttpStatus.UNPROCESSABLE_ENTITY),
    BAD_REQUEST_BODY("요청 형식이 잘못되었습니다.", HttpStatus.UNPROCESSABLE_ENTITY),
    INVALID_TYPE_PARAMETER("파라미터 타입이 유효하지 않습니다.", HttpStatus.UNPROCESSABLE_ENTITY),
    INTERNAL_ERROR("서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
