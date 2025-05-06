package minseok.cqrschallenge.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.common.dto.ApiResponse;
import minseok.cqrschallenge.review.dto.request.ReviewCreateRequest;
import minseok.cqrschallenge.review.dto.request.ReviewUpdateRequest;
import minseok.cqrschallenge.review.dto.response.ReviewListResponse;
import minseok.cqrschallenge.review.dto.response.ReviewResponse;
import minseok.cqrschallenge.review.dto.response.ReviewUpdateResponse;
import minseok.cqrschallenge.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/api/products/{id}/reviews")
    public ResponseEntity<ApiResponse<ReviewListResponse>> getProductReviews(
        @PathVariable("id") Long productId,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int perPage,
        @RequestParam(defaultValue = "created_at:desc") String sort,
        @RequestParam(required = false) Integer rating) {

        ReviewListResponse response = reviewService.getProductReviews(productId, page, perPage,
            sort, rating);

        return ResponseEntity.ok(ApiResponse.success(
            response,
            "상품 리뷰를 성공적으로 조회했습니다."
        ));
    }

    @PostMapping("/api/products/{id}/reviews")
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
        @PathVariable("id") Long productId,
        @Valid @RequestBody ReviewCreateRequest request) {
        Long userId = 1L;

        ReviewResponse response = reviewService.createReview(productId, userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
            response,
            "리뷰가 성공적으로 등록되었습니다."
        ));
    }

    @PutMapping("/api/reviews/{id}")
    public ResponseEntity<ApiResponse<ReviewUpdateResponse>> updateReview(
        @PathVariable("id") Long reviewId,
        @Valid @RequestBody ReviewUpdateRequest request
//            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long userId = 1L;

        ReviewUpdateResponse response = reviewService.updateReview(reviewId, userId, request);

        return ResponseEntity.ok(ApiResponse.success(
            response,
            "리뷰가 성공적으로 수정되었습니다."
        ));
    }

    @DeleteMapping("/api/reviews/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
        @PathVariable("id") Long reviewId
        //   )         @AuthenticationPrincipal UserDetails userDetails
    ) {

        Long userId = 1L;

        reviewService.deleteReview(reviewId, userId);

        return ResponseEntity.ok(ApiResponse.success(
            null,
            "리뷰가 성공적으로 삭제되었습니다."
        ));
    }
}