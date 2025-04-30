package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query;

import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query.dto.ProductReviewSummaryDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationRes;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductRatingDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.review.ProductReviewSearchDataDto;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductReviewSearchReq;
import java.util.List;
import java.util.Map;

public interface ReviewQueryRepository {

    Map<Long, ProductReviewSummaryDto> getProductReviewSummaries(List<Long> productIds);

    ProductRatingDetailDto getProductRatingDetail(Long productId);

    List<ProductReviewSearchDataDto> getSearchProductReviews(Long productId, PaginationReq paginationReq,
                                                             ProductReviewSearchReq req);

    PaginationRes countSearchProductReviews(Long productId, PaginationReq paginationReq, ProductReviewSearchReq req);
}
