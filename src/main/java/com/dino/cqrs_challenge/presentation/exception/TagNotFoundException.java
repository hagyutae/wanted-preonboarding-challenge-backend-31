package com.dino.cqrs_challenge.presentation.exception;

import com.dino.cqrs_challenge.global.exception.CustomException;
import com.dino.cqrs_challenge.global.response.ErrorCode;
import lombok.Getter;

@Getter
public class TagNotFoundException extends CustomException {
    public TagNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND, "태그를 찾을 수 없습니다.", null);
    }
}
