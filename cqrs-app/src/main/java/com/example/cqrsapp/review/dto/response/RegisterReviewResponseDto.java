package com.example.cqrsapp.review.dto.response;

import com.example.cqrsapp.review.domain.Review;
import com.example.cqrsapp.user.domain.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterReviewResponseDto {

    private Long id;
    private UserDto user;
    private Integer rating;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean verifiedPurchase;
    private Integer helpfulVotes;

    public static RegisterReviewResponseDto fromEntity(Review review) {
        return RegisterReviewResponseDto.builder()
                .id(review.getId())
                .user(UserDto.fromEntity(review.getUser()))
                .rating(review.getRating())
                .title(review.getTitle())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .verifiedPurchase(review.getVerifiedPurchase())
                .helpfulVotes(review.getHelpfulVotes())
                .build();
    }

    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UserDto {
        private Long id;
        private String name;
        private String avatarUrl;

        public static UserDto fromEntity(User user) {
            return UserDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .avatarUrl(user.getAvatarUrl())
                    .build();
        }
    }
}
