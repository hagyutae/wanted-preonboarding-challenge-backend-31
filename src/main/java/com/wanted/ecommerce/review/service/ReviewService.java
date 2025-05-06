package com.wanted.ecommerce.review.service;

import com.wanted.ecommerce.review.domain.Review;
import com.wanted.ecommerce.review.dto.response.RatingResponse;
import java.util.List;

public interface ReviewService {

    double getAvgRatingByProductId(Long productId);

    Integer getReviewCountByProductId(Long productId);

    List<Review> getReviews(Long productId);

    RatingResponse createRatingResponse(Long productId);
}
