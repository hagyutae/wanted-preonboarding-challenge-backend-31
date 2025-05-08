package com.example.demo.review.controller;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.util.PageableCreator;
import com.example.demo.review.controller.request.AddReviewRequest;
import com.example.demo.review.controller.request.UpdateReviewRequest;
import com.example.demo.review.dto.AddReviewResult;
import com.example.demo.review.dto.ReviewQueryFilter;
import com.example.demo.review.dto.ReviewSummaryResponse;
import com.example.demo.review.dto.UpdateReviewResult;
import com.example.demo.review.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // TODO : ACCESS TOKEN
    @PostMapping("/products/{id}/reviews")
    public ResponseEntity<ApiResponse<AddReviewResult>> createByProductId(@Positive @PathVariable(name = "id") Long id,
                                                                          @Valid @RequestBody AddReviewRequest addReviewRequest) {
        AddReviewResult addReviewResult = reviewService.addReview(id, addReviewRequest);
        return new ResponseEntity<>(
                ApiResponse.success(addReviewResult, "리뷰가 성공적으로 등록되었습니다."),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/products/{id}/reviews")
    public ResponseEntity<ApiResponse<ReviewSummaryResponse>> findPageByProductId(
            @Positive @PathVariable(name = "id") Long id,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "perPage", defaultValue = "10", required = false) Integer perPage,
            @RequestParam(name = "sort", defaultValue = "created_at:desc", required = false) List<String> sort,
            @RequestParam(name = "rating", required = false) Integer rating
    ) {
        ReviewQueryFilter reviewQueryFilter = ReviewQueryFilter.builder()
                .productId(id)
                .rating(rating)
                .build();
        Pageable pageable = PageableCreator.create(page, perPage, sort);

        ReviewSummaryResponse reviewSummaryResponse = reviewService.findReviewSummaryPage(reviewQueryFilter, pageable);
        return new ResponseEntity<>(
                ApiResponse.success(reviewSummaryResponse, "상품 리뷰를 성공적으로 조회했습니다."),
                HttpStatus.OK);
    }

    // TODO : ACCESS TOKEN
    @PutMapping("/reviews/{id}")
    public ResponseEntity<ApiResponse<UpdateReviewResult>> updateById(@Positive @PathVariable(name = "id") Long id,
                                                                          @Valid @RequestBody UpdateReviewRequest updateReviewRequest) {

        UpdateReviewResult updateReviewResult = reviewService.update(id, updateReviewRequest);
        return new ResponseEntity<>(
                ApiResponse.success(updateReviewResult, "리뷰가 성공적으로 수정되었습니다."),
                HttpStatus.OK
        );
    }

    // TODO : ACCESS TOKEN
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@Positive @PathVariable(name = "id") Long id) {
        reviewService.deleteById(id);
        return new ResponseEntity<>(
                ApiResponse.success(null, "리뷰가 성공적으로 삭제되었습니다."),
                HttpStatus.OK
        );
    }
}
