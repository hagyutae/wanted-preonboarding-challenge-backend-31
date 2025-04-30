package com.mkhwang.wantedcqrs.product.controller;

import com.mkhwang.wantedcqrs.config.advice.ApiMessage;
import com.mkhwang.wantedcqrs.product.application.ReviewService;
import com.mkhwang.wantedcqrs.product.domain.dto.review.*;
import com.mkhwang.wantedcqrs.user.domain.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "Review API")
@RestController
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  @ApiMessage("review.search.success")
  @Operation(summary = "상품 리뷰 조회")
  @GetMapping("/api/products/{id}/reviews")
  public ReviewSearchResultDto getProductReviews(@PathVariable Long id, @ModelAttribute ReviewSearchDto searchDto) {
    return reviewService.getProductReviews(id, searchDto);
  }

  @ApiMessage("review.create.success")
  @Operation(summary = "상품 리뷰 등록")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/api/products/{id}/reviews")
  public ReviewResponseDto postReview(
          @PathVariable Long id,
          @RequestBody ReviewCreateDto reviewDto,
          @AuthenticationPrincipal LoginUser loginUser) {
    reviewDto.setUserId(loginUser.getId());
    reviewDto.setProductId(id);
    return reviewService.postReview(reviewDto);
  }


  @ApiMessage("review.modify.success")
  @Operation(summary = "상품 리뷰 수정")
  @PutMapping("/api/reviews/{id}")
  public ReviewResponseDto updateReview(@PathVariable Long id,
                                        @RequestBody ReviewModifyDto reviewDto,
                                        @AuthenticationPrincipal LoginUser loginUser) {
    reviewDto.setUserId(loginUser.getId());
    reviewDto.setId(id);
    return reviewService.updateReview(reviewDto);
  }

  @ApiMessage("review.delete.success")
  @Operation(summary = "상품 리뷰 삭제")
  @DeleteMapping("/api/reviews/{id}")
  public void deleteReview(@PathVariable Long id, @AuthenticationPrincipal LoginUser loginUser) {
    reviewService.deleteReview(id, loginUser.getId());
  }
}
