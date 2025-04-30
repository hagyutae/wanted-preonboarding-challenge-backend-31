package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductRatingDistributionDto(
        @JsonProperty("5")
        int five,
        @JsonProperty("4")
        int four,
        @JsonProperty("3")
        int three,
        @JsonProperty("2")
        int two,
        @JsonProperty("1")
        int one
) {
}
