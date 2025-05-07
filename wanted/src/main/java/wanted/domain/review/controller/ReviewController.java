package wanted.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.common.response.SuccessResponse;
import wanted.common.security.SecurityUserDetails;
import wanted.domain.review.ReviewSearchCondition;
import wanted.domain.review.dto.request.ProductReviewRequest;
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

    @PostMapping("/products/{id}/reviews")
    public ResponseEntity<?> createProductReview(@PathVariable(value = "id") Long productId, @Valid @RequestBody ProductReviewRequest request,
                                                 @AuthenticationPrincipal SecurityUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.ok(reviewService.createProductReview(productId, request, userDetails.getId())));
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<?> updateReview(@PathVariable(value = "id") Long reviewId, @Valid @RequestBody ProductReviewRequest request,
                                          @AuthenticationPrincipal SecurityUserDetails userDetails) {
        return ResponseEntity.ok(SuccessResponse.ok(reviewService.updateProductReview(reviewId, request, userDetails.getId())));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable(value = "id") Long reviewId, @Valid @RequestBody ProductReviewRequest request,
                                          @AuthenticationPrincipal SecurityUserDetails userDetails) {
        reviewService.deleteProductReview(reviewId, userDetails.getId());
        return ResponseEntity.ok(SuccessResponse.ok(null));
    }
}
