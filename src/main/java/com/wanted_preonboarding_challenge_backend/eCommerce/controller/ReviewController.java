package com.wanted_preonboarding_challenge_backend.eCommerce.controller;

import com.wanted_preonboarding_challenge_backend.eCommerce.common.ApiResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.request.ReviewCreateRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.request.ReviewUpdateRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.response.ReviewCreateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.response.ReviewGetResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.response.ReviewUpdateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/api/products/{id}/reviews")
    public ResponseEntity<ApiResponse<ReviewGetResponse>> getReviewByProduct(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(defaultValue = "created_at:desc") String sort,
            @RequestParam Integer rating) {
        ReviewGetResponse response = reviewService.getReviews(id, page, perPage, sort, rating);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(response, "상품 리뷰를 성공적으로 조회했습니다."));
    }

    @PutMapping("/api/products/{id}/reviews")
    public ResponseEntity<ApiResponse<ReviewCreateResponse>> writeReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewCreateRequest request) {

        ReviewCreateResponse response = reviewService.writeReview(reviewId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "리뷰가 성공적으로 등록되었습니다."));
    }

    @PostMapping("/api/reviews/{id}")
    public ResponseEntity<ApiResponse<ReviewUpdateResponse>> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequest request) {

        ReviewUpdateResponse response = reviewService.updateReview(reviewId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(response, "리뷰가 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/api/reviews/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "리뷰가 성공적으로 삭제되었습니다."));
    }
}
