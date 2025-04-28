package com.mkhwang.wantedcqrs.product.controller;

import com.mkhwang.wantedcqrs.product.application.ReviewService;
import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewDto;
import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewSearchDto;
import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewSearchResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "Review API")
@RestController
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  @Operation(summary = "상품 리뷰 조회")
  @GetMapping("/api/products/{id}/reviews")
  public ReviewSearchResultDto getProductReviews(@PathVariable Long id, @ModelAttribute ReviewSearchDto searchDto) {
    return reviewService.getProductReviews(id, searchDto);
  }

  @Operation(summary = "상품 리뷰 등록")
  @PostMapping("/api/products/{id}/reviews")
  public ReviewDto postReview(@RequestBody ReviewDto reviewDto) {
    return reviewService.postReview(reviewDto);
  }


  @Operation(summary = "상품 리뷰 수정")
  @PutMapping("/api/reviews/{id}")
  public ReviewDto updateReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
    return reviewService.updateReview(id, reviewDto);
  }

  @Operation(summary = "상품 리뷰 삭제")
  @DeleteMapping("/api/reviews/{id}")
  public void deleteReview(@PathVariable Long id) {
    reviewService.deleteReview(id);
  }
}
