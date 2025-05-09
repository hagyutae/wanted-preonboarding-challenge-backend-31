package wanted.shop.review.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wanted.shop.common.api.PaginatedData;
import wanted.shop.common.api.SuccessResponse;
import wanted.shop.product.domain.ProductId;
import wanted.shop.review.domain.query.ReviewPageRequest;
import wanted.shop.review.dto.ReviewDto;
import wanted.shop.review.dto.ReviewListResponse;
import wanted.shop.review.service.ReviewService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ReviewController {

    private ReviewService reviewService;

    @GetMapping("/{id}/reviews")
    public SuccessResponse<ReviewListResponse> getReviewsByProductId(
            @ModelAttribute @Valid ReviewPageRequest request,
            @PathVariable long id
    ) {
        System.out.println(request.toString());
        ReviewListResponse response = reviewService.getReviewsByProductId(new ProductId(id), request);
        return new SuccessResponse<>(response);
    }
}

