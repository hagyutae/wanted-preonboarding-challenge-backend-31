package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationRes;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductRatingDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.review.ProductReviewSearchDataDto;
import java.util.List;

public record ProductReviewSearchRes(
        List<ProductReviewSearchDataDto> items,
        ProductRatingDetailDto summary,
        PaginationRes pagination
) {

    public static ProductReviewSearchRes mergeSummary(ProductReviewSearchRes base, ProductRatingDetailDto summary) {
        return new ProductReviewSearchRes(base.items, summary, base.pagination);
    }
}
