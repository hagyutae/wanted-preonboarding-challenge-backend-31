package com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ReviewCreateResponse {
    private Long id;
    //    private UserSummaryDto user;
    private int rating;
    private String title;
    private String content;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("verified_purchase")
    private boolean verifiedPurchase;

    @JsonProperty("helpful_votes")
    private int helpfulVotes;
}
