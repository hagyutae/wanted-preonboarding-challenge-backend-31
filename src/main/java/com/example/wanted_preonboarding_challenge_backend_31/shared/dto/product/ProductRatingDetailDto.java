package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

public record ProductRatingDetailDto(
        Double average,
        Long count,
        ProductRatingDistributionDto distribution
) {

    public static ProductRatingDetailDto from(ProductRatingDetailDto origin,
                                              ProductRatingDistributionDto distribution) {
        return new ProductRatingDetailDto(origin.average(), origin.count(), distribution);
    }
}
