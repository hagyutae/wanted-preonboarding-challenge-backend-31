package com.example.wanted_preonboarding_challenge_backend_31.application.review;

import com.example.wanted_preonboarding_challenge_backend_31.application.product.ProductQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.application.user.UserQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.Product;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.review.Review;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.user.User;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.ReviewRepository;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductReviewCreateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.request.ReviewUpdateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCommandService {

    private final ProductQueryService productQueryService;
    private final UserQueryService userQueryService;

    private final ReviewRepository reviewRepository;

    public Review saveReview(Long productId, ProductReviewCreateReq req) {
        Product product = productQueryService.getProductById(productId);
        User randomUser = userQueryService.getRandomOne();
        Review review = Review.create(product, randomUser, req.rating(), req.title(), req.content());
        return reviewRepository.save(review);
    }

    public Review updateReview(Review review, ReviewUpdateReq req) {
        review.update(req.rating(), req.title(), req.content());
        return reviewRepository.save(review);
    }

    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }
}
