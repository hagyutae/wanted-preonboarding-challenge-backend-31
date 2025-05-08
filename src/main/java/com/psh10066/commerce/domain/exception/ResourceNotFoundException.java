package com.psh10066.commerce.domain.exception;

import com.psh10066.commerce.api.common.ErrorCode;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final ErrorCode code = ErrorCode.RESOURCE_NOT_FOUND;
    private final Details details;

    public ResourceNotFoundException(String resourceType, Long resourceId) {
        super("요청한 리소스를 찾을 수 없습니다.");
        this.details = new Details(resourceType, String.valueOf(resourceId));
    }

    public record Details(
        String resourceType,
        String resourceId
    ) {
    }
}
