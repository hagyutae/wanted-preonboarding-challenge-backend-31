package com.example.wanted_preonboarding_challenge_backend_31.web.review.dto;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.SuccessType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReviewSuccessType implements SuccessType {
    REVIEW_UPDATE("리뷰가 성공적으로 수정되었습니다."),
    REVIEW_DELETE("리뷰가 성공적으로 삭제되었습니다.");

    private final String message;

    @Override
    public String getMessage() {
        return this.message;
    }
}
