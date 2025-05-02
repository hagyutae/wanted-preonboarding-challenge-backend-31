package com.dino.cqrs_challenge.presentation.exception;

import com.dino.cqrs_challenge.global.exception.CustomException;
import com.dino.cqrs_challenge.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class BrandNotFoundException extends CustomException {
    public BrandNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND, "브랜드를 찾을 수 없습니다.", null);
    }
}
