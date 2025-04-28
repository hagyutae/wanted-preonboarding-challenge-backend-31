package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewDto;
import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewSearchDto;
import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewSearchResultDto;
import com.mkhwang.wantedcqrs.product.infra.ReviewRepository;
import com.mkhwang.wantedcqrs.product.infra.ReviewSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final ReviewSearchRepository reviewSearchRepository;

  public ReviewSearchResultDto getProductReviews(Long productId, ReviewSearchDto searchDto) {
    return reviewSearchRepository.findRatingByProductId(productId, searchDto);
  }

  @Transactional
  public ReviewDto postReview(ReviewDto reviewDto) {
    return null;
  }

  @Transactional
  public void deleteReview(Long id) {
    reviewRepository.deleteById(id);
  }

  @Transactional
  public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
    return null;
  }
}
