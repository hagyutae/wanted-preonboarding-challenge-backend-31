package com.psh10066.commerce.infrastructure.dao.review;

import com.psh10066.commerce.api.dto.request.GetProductReviewsRequest;
import com.psh10066.commerce.api.dto.response.GetAllReviewsResponse;
import org.springframework.data.domain.Page;

public interface ReviewCustomRepository {

    Page<GetAllReviewsResponse> getProductReviews(Long productId, GetProductReviewsRequest request);
}
