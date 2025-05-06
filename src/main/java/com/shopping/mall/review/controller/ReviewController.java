package com.shopping.mall.review.controller;


import com.shopping.mall.common.response.ApiResponse;
import com.shopping.mall.review.dto.request.ReviewCreateRequest;
import com.shopping.mall.review.dto.request.ReviewSearchCondition;
import com.shopping.mall.review.dto.request.ReviewUpdateRequest;
import com.shopping.mall.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /** 리뷰 작성 */
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<?> createReview(
            @PathVariable Long productId,
            @RequestBody ReviewCreateRequest request) {

        return ResponseEntity.ok(reviewService.createReview(productId, request));
    }

    /** 리뷰 조회 */
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<?> getReviews(
            @PathVariable Long productId,
            ReviewSearchCondition condition) {

        ApiResponse<?> response = reviewService.getReviews(productId, condition);
        return ResponseEntity.ok(response);
    }

    /** 리뷰 수정 */
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<?> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequest request) {

        reviewService.updateReview(reviewId, request);
        return ResponseEntity.ok(ApiResponse.success(null, "리뷰가 성공적으로 수정되었습니다."));
    }

    /** 리뷰 삭제 */
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(ApiResponse.success(null, "리뷰가 성공적으로 삭제되었습니다."));
    }
}