package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.product.domain.dto.ReviewDto;
import com.mkhwang.wantedcqrs.product.domain.dto.ReviewSearchDto;
import com.mkhwang.wantedcqrs.product.infra.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;

  public List<ReviewDto> getProductReviews(Long id, ReviewSearchDto searchDto) {
    return null;
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
