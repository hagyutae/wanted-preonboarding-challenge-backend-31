package com.shopping.mall.review.service;

import com.shopping.mall.common.exception.ResourceNotFoundException;
import com.shopping.mall.common.response.ApiResponse;
import com.shopping.mall.product.entity.Product;
import com.shopping.mall.product.repository.ProductRepository;
import com.shopping.mall.review.dto.request.ReviewCreateRequest;
import com.shopping.mall.review.entity.Review;
import com.shopping.mall.review.repository.ReviewRepository;
import com.shopping.mall.user.entity.User;
import com.shopping.mall.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
