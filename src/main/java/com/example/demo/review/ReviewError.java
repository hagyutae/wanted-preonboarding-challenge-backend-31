package com.example.demo.review;

import com.example.demo.common.exception.BusinessError;
import lombok.Getter;

@Getter
public enum ReviewError implements BusinessError {
    REVIEW_ADD_FAIL("리뷰 등록에 실패하였습니다."),
    UPDATE_FAIL("리뷰 수정에 실패하였습니다.");

    private final String detailMessage;

    ReviewError(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
