package com.preonboarding.dto.response.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.preonboarding.domain.Review;
import com.preonboarding.dto.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductReviewPageResponse {
    private Long id;
    private UserResponse user;
    private Integer rating;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean verifiedPurchase;
    private Integer helpfulVotes;

    public static ProductReviewPageResponse of(Review review) {
        UserResponse userResponse = UserResponse.of(review.getUser());

        return ProductReviewPageResponse.builder()
                .id(review.getId())
                .user(userResponse)
                .rating(review.getRating())
                .title(review.getTitle())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .verifiedPurchase(review.getVerifiedPurchase())
                .helpfulVotes(review.getHelpfulVotes())
                .build();
    }
}
