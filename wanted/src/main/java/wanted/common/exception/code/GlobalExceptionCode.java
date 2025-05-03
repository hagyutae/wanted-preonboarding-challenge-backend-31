package wanted.common.exception.code;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum GlobalExceptionCode implements ExceptionCode{
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT", "잘못된 입력 데이터", null),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", "요청한 리소스를 찾을 수 없음", null),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "인증되지 않은 요청", null),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "권한이 없는 요청", null),
    CONFLICT(HttpStatus.CONFLICT, "CONFLICT", "리소스 충돌 발생", null),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "서버 내부 오류", null);

    private final HttpStatus status;
    private final String code;
    private final String message;
    private final String details;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getDetails() {
        return details;
    }
}
