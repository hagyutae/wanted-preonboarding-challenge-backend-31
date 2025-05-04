package com.preonboarding.dto.response.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.preonboarding.domain.Review;
import com.preonboarding.dto.response.User.UserResponse;
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
public class ProductReviewResponse {
    private Long id;
    private UserResponse user;
    private Integer rating;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean verifiedPurchase;
    private Integer helpfulVotes;

    public static ProductReviewResponse from(Review review,UserResponse userResponse) {
        return ProductReviewResponse.builder()
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
