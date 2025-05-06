package com.sandro.wanted_shop.review.web;

import com.sandro.wanted_shop.common.dto.IdResponse;
import com.sandro.wanted_shop.review.CreateReviewCommand;
import com.sandro.wanted_shop.review.ReviewService;
import com.sandro.wanted_shop.review.UpdateReviewCommand;
import com.sandro.wanted_shop.review.dto.ReviewDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/api/products/{id}/reviews")
    public List<ReviewDto> getAllReviews(@PathVariable Long id) {
        return reviewService.getAllReviews(id);
    }

    @PostMapping("/api/products/{id}/reviews")
    public IdResponse createReview(@PathVariable Long id, @Valid @RequestBody CreateReviewCommand command) {
        return IdResponse.of(reviewService.create(id, command));
    }

    @PutMapping("/api/reviews/{id}")
    public void updateReview(@PathVariable Long id, @Valid @RequestBody UpdateReviewCommand command) {
        reviewService.update(id, command);
    }

    @DeleteMapping("/api/reviews/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
    }
}
