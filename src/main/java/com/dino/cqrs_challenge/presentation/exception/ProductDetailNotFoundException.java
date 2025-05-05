package com.dino.cqrs_challenge.presentation.exception;

import com.dino.cqrs_challenge.global.exception.CustomException;
import com.dino.cqrs_challenge.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class ProductDetailNotFoundException extends CustomException {
    public ProductDetailNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND, "요청한 상품 상세정보를 찾을 수 없습니다.", null);
    }
}
