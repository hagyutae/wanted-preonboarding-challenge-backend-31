package com.example.demo.review.dto;

import java.util.Map;

public record ReviewSummaryDistribution(
        Double averageRating,
        Long totalCount,
        Map<String, Long> distribution
) {
    public static ReviewSummaryDistribution of(ReviewDistribution reviewDistribution) {
        return new ReviewSummaryDistribution(
                reviewDistribution.getAverage(),
                reviewDistribution.getTotal(),
                Map.of("5", reviewDistribution.five(),
                        "4", reviewDistribution.four(),
                        "3", reviewDistribution.three(),
                        "2", reviewDistribution.two(),
                        "1", reviewDistribution.one())
        );
    }
}
