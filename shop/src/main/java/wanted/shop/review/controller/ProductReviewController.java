package wanted.shop.review.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.shop.common.api.Message;
import wanted.shop.common.api.SuccessResponse;
import wanted.shop.product.domain.entity.ProductId;
import wanted.shop.review.domain.query.ReviewPageRequest;
import wanted.shop.review.dto.ReviewDataRequest;
import wanted.shop.review.dto.ReviewDto;
import wanted.shop.review.dto.ReviewListResponse;
import wanted.shop.review.service.ReviewService;
import wanted.shop.user.domain.UserId;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}/reviews")
    public SuccessResponse<ReviewListResponse
            > getReviewsByProductId(
            @ModelAttribute @Valid ReviewPageRequest request,
            @PathVariable Long productId
    ) {
        System.out.println(request.toString());
        ReviewListResponse response = reviewService.getReviewsByProductId(new ProductId(productId), request);
        return new SuccessResponse<>(response);
    }

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<SuccessResponse<Object>> createReview(
            @PathVariable Long productId,
            @RequestBody ReviewDataRequest reviewDataRequest) {

        // 임의의 유저
        ReviewDto response = reviewService.createReview(reviewDataRequest, new UserId(1L), new ProductId(productId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>(response, new Message("리뷰가 성공적으로 등록되었습니다.")));
    }
}

