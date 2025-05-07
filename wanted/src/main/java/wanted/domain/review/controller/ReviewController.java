package wanted.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.common.response.SuccessResponse;
import wanted.domain.review.ReviewSearchCondition;
import wanted.domain.review.service.ReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/products/{id}/reviews")
    public ResponseEntity<?> getProductReviews(@PathVariable(value = "id") Long productId, @ModelAttribute ReviewSearchCondition condition){
        return ResponseEntity.ok(SuccessResponse.ok(reviewService.getProductReviews(productId, condition)));
    }
}
