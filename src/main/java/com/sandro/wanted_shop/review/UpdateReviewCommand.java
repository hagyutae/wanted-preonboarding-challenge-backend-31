package com.sandro.wanted_shop.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateReviewCommand(
        @NotNull @Min(1) @Max(5)
        Integer rating,
        String title,
        String content,
        Boolean verifiedPurchase,
        Integer helpfulVotes
) {
}
