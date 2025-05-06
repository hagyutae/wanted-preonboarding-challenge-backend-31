package com.shopping.mall.review.service;

import com.shopping.mall.common.exception.ResourceNotFoundException;
import com.shopping.mall.common.response.ApiResponse;
import com.shopping.mall.product.entity.Product;
import com.shopping.mall.product.repository.ProductRepository;
import com.shopping.mall.review.dto.request.ReviewCreateRequest;
import com.shopping.mall.review.dto.request.ReviewSearchCondition;
import com.shopping.mall.review.dto.request.ReviewUpdateRequest;
import com.shopping.mall.review.dto.response.ReviewListResponse;
import com.shopping.mall.review.dto.response.ReviewResponse;
import com.shopping.mall.review.dto.response.ReviewSummaryResponse;
import com.shopping.mall.review.entity.Review;
import com.shopping.mall.review.repository.ReviewRepository;
import com.shopping.mall.user.entity.User;
import com.shopping.mall.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApiResponse<?> createReview(Long productId, ReviewCreateRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("상품을 찾을 수 없습니다."));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));

        Review review = Review.builder()
                .product(product)
                .user(user)
                .rating(request.getRating())
                .title(request.getTitle())
                .content(request.getContent())
                .verifiedPurchase(request.getVerifiedPurchase())
                .helpfulVotes(0)
                .build();

        reviewRepository.save(review);

        return ApiResponse.success(null, "리뷰가 성공적으로 등록되었습니다.");
    }

    public ApiResponse<?> getReviews(Long productId, ReviewSearchCondition condition) {

        int page = condition.getPage();
        int perPage = condition.getPerPage();
        int offset = (page - 1) * perPage;

        // 리뷰 목록 조회
        List<Review> reviews = reviewRepository.findByProductId(
                productId, condition.getRating(), offset, perPage
        );

        Long totalCount = reviewRepository.countByProductId(productId, condition.getRating());

        // 통계 조회
        Map<Integer, Long> distribution = reviewRepository.getRatingDistribution(productId);
        Double averageRating = reviewRepository.getAverageRating(productId);

        List<ReviewResponse> items = reviews.stream().map(r ->
                ReviewResponse.builder()
                        .id(r.getId())
                        .userId(r.getUser().getId())
                        .userName(r.getUser().getName())
                        .avatarUrl(r.getUser().getAvatarUrl())
                        .rating(r.getRating())
                        .title(r.getTitle())
                        .content(r.getContent())
                        .verifiedPurchase(r.getVerifiedPurchase())
                        .helpfulVotes(r.getHelpfulVotes())
                        .createdAt(r.getCreatedAt())
                        .updatedAt(r.getUpdatedAt())
                        .build()
        ).toList();

        ReviewSummaryResponse summary = ReviewSummaryResponse.builder()
                .averageRating(averageRating)
                .totalCount(totalCount)
                .distribution(distribution)
                .build();

        ReviewListResponse response = ReviewListResponse.builder()
                .items(items)
                .summary(summary)
                .pagination(ReviewListResponse.PaginationResponse.builder()
                        .totalItems(totalCount)
                        .totalPages((int) Math.ceil((double) totalCount / perPage))
                        .currentPage(page)
                        .perPage(perPage)
                        .build())
                .build();

        return ApiResponse.success(response);
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("리뷰를 찾을 수 없습니다."));

        review.update(request.getTitle(), request.getContent(), request.getRating());
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("리뷰를 찾을 수 없습니다."));

        reviewRepository.delete(review);
    }
}
