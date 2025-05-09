package com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ReviewUpdateResponse {
    private Long id;

    private int rating;
    private String title;
    private String content;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

}
