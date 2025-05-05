package com.example.cqrsapp.review.service;

import com.example.cqrsapp.common.dto.ReviewRatingDto;
import com.example.cqrsapp.common.exception.ResourceNotFoundException;
import com.example.cqrsapp.product.domain.Product;
import com.example.cqrsapp.product.repository.ProductRepository;
import com.example.cqrsapp.review.domain.Review;
import com.example.cqrsapp.review.dto.request.RegisterReviewDto;
import com.example.cqrsapp.review.dto.request.UpdateReviewDto;
import com.example.cqrsapp.review.dto.response.RegisterReviewResponseDto;
import com.example.cqrsapp.review.dto.response.ReviewListResponse;
import com.example.cqrsapp.review.dto.response.UpdateReviewResponseDto;
import com.example.cqrsapp.review.repository.ReviewRepository;
import com.example.cqrsapp.user.domain.User;
import com.example.cqrsapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.cqrsapp.common.dto.ReviewRatingDto.DistributionDto;
import static com.example.cqrsapp.review.dto.response.ReviewListResponse.ReviewItemDto;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ReviewListResponse searchReview(Long productId, Integer rating, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByProductIdAndRating(productId, rating, pageable);
        ReviewRatingDto ratingDto = reviewRepository.findReviewRatingByProductIdAndRating(productId, rating);
        List<DistributionDto> distribution = reviewRepository.findReviewDistributionByProductIdAndRating(productId,rating);
        Page<ReviewItemDto> item = reviews.map(ReviewItemDto::fromEntity);
        return ReviewListResponse.of(item, ratingDto, distribution);
    }

    @Transactional
    public RegisterReviewResponseDto createReview(long userId, Long productId, RegisterReviewDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", String.valueOf(userId)));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", String.valueOf(productId)));

        Review review = reviewRepository.save(dto.toEntity(user, product));
        return RegisterReviewResponseDto.fromEntity(review);
    }

    public UpdateReviewResponseDto updateReview(Long reviewId, UpdateReviewDto dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", String.valueOf(reviewId)));
        review.changeReview(dto.getTitle(), dto.getRating(), dto.getContent());
        reviewRepository.save(review);
        return UpdateReviewResponseDto.fromEntity(review);
    }

}
