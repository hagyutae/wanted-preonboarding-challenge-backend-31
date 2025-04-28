package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.config.exception.ForbiddenException;
import com.mkhwang.wantedcqrs.config.exception.ResourceNotFoundException;
import com.mkhwang.wantedcqrs.product.domain.Product;
import com.mkhwang.wantedcqrs.product.domain.Review;
import com.mkhwang.wantedcqrs.product.domain.dto.review.*;
import com.mkhwang.wantedcqrs.product.infra.ProductRepository;
import com.mkhwang.wantedcqrs.product.infra.ReviewRepository;
import com.mkhwang.wantedcqrs.product.infra.ReviewSearchRepository;
import com.mkhwang.wantedcqrs.user.domain.User;
import com.mkhwang.wantedcqrs.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final ReviewSearchRepository reviewSearchRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final GenericMapper genericMapper;

  public ReviewSearchResultDto getProductReviews(Long productId, ReviewSearchDto searchDto) {
    return reviewSearchRepository.findRatingByProductId(productId, searchDto);
  }

  @Transactional
  public ReviewResponseDto postReview(ReviewCreateDto reviewDto) {
    User existUser = userRepository.findById(reviewDto.getUserId()).orElseThrow(
            () -> new ResourceNotFoundException("user.not.found", null));
    Product existProduct = productRepository.findById(reviewDto.getProductId()).orElseThrow(
            () -> new ResourceNotFoundException("product.not.found", null)
    );

    reviewDto.setUser(existUser);
    reviewDto.setProduct(existProduct);
    Review review = Review.from(reviewDto);
    reviewRepository.save(review);
    return genericMapper.toDto(review, ReviewResponseDto.class);
  }

  @Transactional
  public void deleteReview(Long id, Long userId) {
    Review existReview = reviewRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("review.not.found", null));
    if (!existReview.getUser().getId().equals(userId)) {
      throw new ForbiddenException("review.modify.have.no.authorities", null);
    }
    reviewRepository.deleteById(id);
  }

  @Transactional
  public ReviewResponseDto updateReview(ReviewModifyDto reviewDto) {
    Review existReview = reviewRepository.findById(reviewDto.getId()).orElseThrow(
            () -> new ResourceNotFoundException("review.not.found", null));
    if (!existReview.getUser().getId().equals(reviewDto.getUserId())) {
      throw new ForbiddenException("review.modify.have.no.authorities", null);
    }

    existReview.setTitle(reviewDto.getTitle());
    existReview.setContent(reviewDto.getContent());
    existReview.setRating(reviewDto.getRating());
    reviewRepository.save(existReview);

    return genericMapper.toDto(existReview, ReviewResponseDto.class);
  }
}
