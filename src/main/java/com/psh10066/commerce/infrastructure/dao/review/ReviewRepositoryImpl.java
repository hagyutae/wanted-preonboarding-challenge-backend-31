package com.psh10066.commerce.infrastructure.dao.review;

import com.psh10066.commerce.api.dto.request.GetProductReviewsRequest;
import com.psh10066.commerce.api.dto.response.GetAllReviewsResponse;
import com.psh10066.commerce.domain.exception.ResourceNotFoundException;
import com.psh10066.commerce.domain.model.review.Review;
import com.psh10066.commerce.domain.model.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public List<Review> findAllByProductId(Long productId) {
        return reviewJpaRepository.findAllByProductId(productId);
    }

    @Override
    public Page<GetAllReviewsResponse> getProductReviews(Long productId, GetProductReviewsRequest request) {
        return reviewJpaRepository.getProductReviews(productId, request);
    }

    @Override
    public Review save(Review review) {
        return reviewJpaRepository.save(review);
    }

    @Override
    public Review getById(Long id) {
        return reviewJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Review", id));
    }
}
