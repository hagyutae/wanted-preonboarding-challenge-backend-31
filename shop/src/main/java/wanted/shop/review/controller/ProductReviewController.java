package wanted.shop.review.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wanted.shop.common.api.SuccessResponse;
import wanted.shop.product.domain.ProductId;
import wanted.shop.review.domain.query.ReviewPageRequest;
import wanted.shop.review.dto.ReviewListResponse;
import wanted.shop.review.service.ReviewService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}/reviews")
    public SuccessResponse<ReviewListResponse> getReviewsByProductId(
            @ModelAttribute @Valid ReviewPageRequest request,
            @PathVariable Long productId
    ) {
        System.out.println(request.toString());
        ReviewListResponse response = reviewService.getReviewsByProductId(new ProductId(productId), request);
        return new SuccessResponse<>(response);
    }

}

