package com.preonboarding.controller;

import com.preonboarding.dto.request.review.ProductReviewRequestDto;
import com.preonboarding.dto.response.review.ProductReviewPageResponse;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductReviewPageResponse>> editReview(@PathVariable("id") Long id, @RequestBody ProductReviewRequestDto dto) {
        return ResponseEntity.ok(reviewService.editReview(id,dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse<ProductReviewPageResponse>> deleteReview(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reviewService.deleteReview(id));
    }
}
