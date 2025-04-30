package com.example.wanted_preonboarding_challenge_backend_31.application.review;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.review.Review;
import com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.request.ReviewUpdateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.response.ReviewUpdateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;

    public ReviewUpdateRes update(Long reviewId, ReviewUpdateReq req) {
        Review review = reviewQueryService.getReviewById(reviewId);
        review = reviewCommandService.updateReview(review, req);

        return ReviewUpdateRes.from(review);
    }

    public void delete(Long reviewId) {
        Review review = reviewQueryService.getReviewById(reviewId);

        reviewCommandService.deleteReview(review);
    }
}
