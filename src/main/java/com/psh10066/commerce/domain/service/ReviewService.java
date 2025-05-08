package com.psh10066.commerce.domain.service;

import com.psh10066.commerce.api.dto.request.CreateReviewRequest;
import com.psh10066.commerce.api.dto.request.GetProductReviewsRequest;
import com.psh10066.commerce.api.dto.request.UpdateReviewRequest;
import com.psh10066.commerce.api.dto.response.CreateReviewResponse;
import com.psh10066.commerce.api.dto.response.GetAllReviewsResponse;
import com.psh10066.commerce.api.dto.response.PaginationWithSummaryResponse;
import com.psh10066.commerce.api.dto.response.UpdateReviewResponse;
import com.psh10066.commerce.domain.model.product.Product;
import com.psh10066.commerce.domain.model.product.ProductRepository;
import com.psh10066.commerce.domain.model.review.Review;
import com.psh10066.commerce.domain.model.review.ReviewFirstCollection;
import com.psh10066.commerce.domain.model.review.ReviewRepository;
import com.psh10066.commerce.domain.model.user.User;
import com.psh10066.commerce.domain.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public PaginationWithSummaryResponse<GetAllReviewsResponse> getProductReviews(Long productId, GetProductReviewsRequest request) {
        List<Review> totalReviews = reviewRepository.findAllByProductId(productId);
        Page<GetAllReviewsResponse> reviews = reviewRepository.getProductReviews(productId, request);
        return PaginationWithSummaryResponse.of(new ReviewFirstCollection(totalReviews), reviews, review -> review);
    }

    @Transactional
    public CreateReviewResponse createReview(Long productId, CreateReviewRequest request) {
        Product product = productRepository.getById(productId);
        User user = userRepository.getById(1L);// TODO

        Review review = new Review(product, user, request.rating(), request.title(), request.content());
        reviewRepository.save(review);

        return new CreateReviewResponse(
            review.getId(),
            new CreateReviewResponse.UserDto(user.getId(), user.getName(), user.getAvatarUrl()),
            review.getRating(),
            review.getTitle(),
            review.getContent(),
            review.getCreatedAt(),
            review.getUpdatedAt(),
            review.getVerifiedPurchase(),
            review.getHelpfulVotes()
        );
    }

    @Transactional
    public UpdateReviewResponse updateReview(Long id, UpdateReviewRequest request) {
        Review review = reviewRepository.getById(id);
        review.update(request.rating(), request.title(), request.content());
        reviewRepository.save(review);
        return new UpdateReviewResponse(review.getId(), review.getRating(), review.getTitle(), review.getContent(), review.getUpdatedAt());
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
