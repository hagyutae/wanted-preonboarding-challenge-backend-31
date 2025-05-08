package com.example.demo.review.dto;

import com.example.demo.review.entity.ReviewEntity;
import com.example.demo.user.entity.UserEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ReviewSummary(
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
    public static ReviewSummary of(ReviewEntity reviewEntity) {
        return new ReviewSummary(
                reviewEntity.getId(),
                User.of(reviewEntity.getUserEntity()),
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
    ) {
        public static User of(UserEntity userEntity) {
            return new User(
                    userEntity.getId(),
                    userEntity.getName(),
                    userEntity.getAvatarUrl()
            );
        }
    }
}
