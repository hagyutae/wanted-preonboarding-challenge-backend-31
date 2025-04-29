package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query;

import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query.dto.ProductReviewSummaryDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductRatingDetailDto;
import java.util.List;
import java.util.Map;

public interface ReviewQueryRepository {

    Map<Long, ProductReviewSummaryDto> getProductReviewSummaries(List<Long> productIds);

    ProductRatingDetailDto getProductRatingDetail(Long productId);
}
