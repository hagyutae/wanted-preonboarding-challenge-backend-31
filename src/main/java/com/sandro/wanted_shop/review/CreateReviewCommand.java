package com.sandro.wanted_shop.review;

import com.sandro.wanted_shop.product.entity.Product;
import com.sandro.wanted_shop.review.entity.Review;
import com.sandro.wanted_shop.review.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateReviewCommand(
        @NotNull
        Long userId,
        @NotNull @Min(1) @Max(5)
        Integer rating,
        String title,
        String content,
        Boolean verifiedPurchase,

        Integer helpfulVotes
) {
    public Review toEntity(Product product, User reviewer) {
        return Review.builder()
                .product(product)
                .user(reviewer)
                .rating(rating)
                .title(title)
                .content(content)
                .verifiedPurchase(verifiedPurchase)
                .helpfulVotes(helpfulVotes)
                .build();
    }
}
