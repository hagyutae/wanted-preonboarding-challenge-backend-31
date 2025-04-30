package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.review.Review;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.user.UserInfoDto;
import java.time.LocalDateTime;

public record ProductReviewCreateRes(
        Long id,
        UserInfoDto user,
        int rating,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean verifiedPurchase,
        int helpfulVotes
) {

    public static ProductReviewCreateRes from(Review review) {
        return new ProductReviewCreateRes(
                review.getId(),
                UserInfoDto.from(review.getUser()),
                review.getRating(),
                review.getTitle(),
                review.getContent(),
                review.getCreatedAt(),
                review.getUpdatedAt(),
                review.isVerifiedPurchase(),
                review.getHelpfulVotes()
        );
    }
}
