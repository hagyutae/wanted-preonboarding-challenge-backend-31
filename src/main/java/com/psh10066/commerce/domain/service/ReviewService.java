package com.psh10066.commerce.domain.service;

import com.psh10066.commerce.api.dto.request.GetProductReviewsRequest;
import com.psh10066.commerce.api.dto.response.GetAllReviewsResponse;
import com.psh10066.commerce.api.dto.response.PaginationWithSummaryResponse;
import com.psh10066.commerce.domain.model.review.Review;
import com.psh10066.commerce.domain.model.review.ReviewFirstCollection;
import com.psh10066.commerce.domain.model.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public PaginationWithSummaryResponse<GetAllReviewsResponse> getProductReviews(Long productId, GetProductReviewsRequest request) {
        List<Review> totalReviews = reviewRepository.findAllByProductId(productId);
        Page<GetAllReviewsResponse> reviews = reviewRepository.getProductReviews(productId, request);
        return PaginationWithSummaryResponse.of(new ReviewFirstCollection(totalReviews), reviews, review -> review);
    }
}
