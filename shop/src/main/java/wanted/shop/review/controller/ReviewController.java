package wanted.shop.review.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wanted.shop.common.api.PaginatedData;
import wanted.shop.common.api.SuccessResponse;
import wanted.shop.review.domain.ProductId;
import wanted.shop.review.domain.Review;
import wanted.shop.review.dto.ReviewPageRequest;
import wanted.shop.review.service.ReviewService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ReviewController {

    private ReviewService reviewService;

    @GetMapping("/{id}/reviews")
    public SuccessResponse<PaginatedData<Review>> getReviewsByProductId(
            @ModelAttribute @Valid ReviewPageRequest request,
            @PathVariable long id
    ) {
        System.out.println(request.toString());
        PaginatedData<Review> result = reviewService.getReviewsByProductId(new ProductId(id), request);
        return new SuccessResponse<>(result);
    }
}

