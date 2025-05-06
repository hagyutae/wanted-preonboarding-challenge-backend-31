package minseok.cqrschallenge.review.service;

import minseok.cqrschallenge.review.dto.request.ReviewCreateRequest;
import minseok.cqrschallenge.review.dto.request.ReviewUpdateRequest;
import minseok.cqrschallenge.review.dto.response.ReviewListResponse;
import minseok.cqrschallenge.review.dto.response.ReviewResponse;
import minseok.cqrschallenge.review.dto.response.ReviewUpdateResponse;

public interface ReviewService {

    ReviewListResponse getProductReviews(Long productId, int page, int perPage, String sort,
        Integer rating);

    ReviewResponse createReview(Long productId, Long userId, ReviewCreateRequest request);

    ReviewUpdateResponse updateReview(Long reviewId, Long userId, ReviewUpdateRequest request);

    void deleteReview(Long reviewId, Long userId);
}