package com.wanted.mono.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_INPUT("INVALID_INPUT", HttpStatus.BAD_REQUEST, "error.invalid_input"),
    INVALID_TYPE_INPUT("INVALID_TYPE_INPUT", HttpStatus.BAD_REQUEST, "error.invalid_type_input"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND, "error.resource_not_found"),
    UNAUTHORIZED("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, "error.unauthorized"),
    FORBIDDEN("FORBIDDEN", HttpStatus.FORBIDDEN, "error.forbidden"),
    CONFLICT("CONFLICT", HttpStatus.CONFLICT, "error.conflict"),
    INTERNAL_ERROR("INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "error.internal_error"),
    PRODUCT_EMPTY("RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND, "error.product.list_empty"),
    CATEGORY_EMPTY("RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND, "error.category.empty");
    private final String code;
    private final HttpStatus status;
    private final String messageKey;
}
