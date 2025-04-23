package com.wanted.ecommerce.common.exception;

import com.wanted.ecommerce.common.constants.ErrorCodeConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
public enum ErrorType {

    /**
     * 400 : 잘못된 입력 데이터
     */
    INVALID_INPUT(ErrorCodeConstants.INVALID_INPUT, HttpStatus.BAD_REQUEST),
    /**
     * 404 : 요청한 리소스를 찾을 수 없음
     */
    RESOURCE_NOT_FOUND(ErrorCodeConstants.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND),
    /**
     * 401 : 인증되지 않은 요청
     */
    UNAUTHORIZED(ErrorCodeConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED),
    /**
     * 403 : 권한이 없는 요청
     */
    FORBIDDEN(ErrorCodeConstants.FORBIDDEN, HttpStatus.FORBIDDEN),
    /**
     * 409 : 리소스 충돌 발생
     */
    CONFLICT(ErrorCodeConstants.CONFLICT, HttpStatus.CONFLICT),
    /**
     * 500 : 서버 내부 오류
     */
    INTERNAL_ERROR(ErrorCodeConstants.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);

    private final String codeName;
    private final HttpStatusCode statusCode;
}
