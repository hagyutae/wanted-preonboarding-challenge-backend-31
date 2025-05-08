package com.example.demo.review.dto;

import com.example.demo.review.entity.ReviewEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdateReviewResult(
        Long id,
        User user,
        Integer rating,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean verifiedPurchase,
        Integer helpfulVotes
) {
    public static UpdateReviewResult of(ReviewEntity reviewEntity) {
        return new UpdateReviewResult(
                reviewEntity.getId(),
                null,
                reviewEntity.getRating(),
                reviewEntity.getTitle(),
                reviewEntity.getContent(),
                reviewEntity.getCreatedAt(),
                reviewEntity.getUpdatedAt(),
                reviewEntity.getVerifiedPurchase(),
                reviewEntity.getHelpfulVotes()
        );
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record User(
            Long id,
            String name,
            String avatarUrl
    ) {}
}
