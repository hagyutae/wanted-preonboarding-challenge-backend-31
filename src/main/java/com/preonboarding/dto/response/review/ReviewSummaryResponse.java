package com.preonboarding.dto.response.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewSummaryResponse {
    private double averageRating;
    private long totalCount;
    private DistributionDto distribution;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DistributionDto {
        @JsonProperty("5")
        private int rating5;

        @JsonProperty("4")
        private int rating4;

        @JsonProperty("3")
        private int rating3;

        @JsonProperty("2")
        private int rating2;

        @JsonProperty("1")
        private int rating1;
    }
}
