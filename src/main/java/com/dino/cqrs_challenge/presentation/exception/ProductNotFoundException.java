package com.dino.cqrs_challenge.presentation.exception;

import com.dino.cqrs_challenge.global.exception.CustomException;
import com.dino.cqrs_challenge.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class ProductNotFoundException extends CustomException {
    public ProductNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND, "요청한 상품을 찾을 수 없습니다.", null);
    }
}
