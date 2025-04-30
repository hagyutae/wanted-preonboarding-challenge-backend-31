package com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.review.Review;
import java.time.LocalDateTime;

public record ReviewUpdateRes(
        Long id,
        int rating,
        String title,
        String content,
        LocalDateTime updatedAt
) {

    public static ReviewUpdateRes from(Review review) {
        return new ReviewUpdateRes(
                review.getId(),
                review.getRating(),
                review.getTitle(),
                review.getContent(),
                review.getUpdatedAt()
        );
    }
}
