package com.shopping.mall.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponse {

    private Long id;

    private Long userId;
    private String userName;
    private String avatarUrl;

    private Integer rating;
    private String title;
    private String content;

    private Boolean verifiedPurchase;
    private Integer helpfulVotes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}