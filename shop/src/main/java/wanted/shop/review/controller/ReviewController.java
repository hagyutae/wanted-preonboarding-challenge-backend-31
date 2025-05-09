package wanted.shop.review.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.shop.common.api.Message;
import wanted.shop.common.api.SuccessResponse;
import wanted.shop.review.domain.entity.ReviewId;
import wanted.shop.review.service.ReviewService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @DeleteMapping("/{reviewId}")
    public SuccessResponse<Void> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(new ReviewId(reviewId));
        return new SuccessResponse<>(null, new Message("리뷰가 성공적으로 삭제되었습니다"));
    }
}
