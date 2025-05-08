package com.psh10066.commerce.domain.model.review;

import com.psh10066.commerce.api.dto.request.GetProductReviewsRequest;
import com.psh10066.commerce.api.dto.response.GetAllReviewsResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewRepository {

    List<Review> findAllByProductId(Long productId);

    Page<GetAllReviewsResponse> getProductReviews(Long productId, GetProductReviewsRequest request);

    Review save(Review review);

    Review getById(Long id);
}
