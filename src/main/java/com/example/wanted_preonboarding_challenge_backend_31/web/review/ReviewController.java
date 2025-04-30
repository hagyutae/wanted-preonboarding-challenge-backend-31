package com.example.wanted_preonboarding_challenge_backend_31.web.review;

import static com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.ReviewSuccessType.REVIEW_DELETE;
import static com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.ReviewSuccessType.REVIEW_UPDATE;

import com.example.wanted_preonboarding_challenge_backend_31.application.review.ReviewService;
import com.example.wanted_preonboarding_challenge_backend_31.common.dto.SuccessRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.request.ReviewUpdateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.response.ReviewUpdateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PutMapping("/{id}")
    public SuccessRes<ReviewUpdateRes> update(
            @PathVariable("id") Long reviewId,
            @RequestBody @Validated ReviewUpdateReq reviewUpdateReq
    ) {
        return SuccessRes.of(REVIEW_UPDATE, reviewService.update(reviewId, reviewUpdateReq));
    }

    @DeleteMapping("/{id}")
    public SuccessRes<?> delete(@PathVariable("id") Long reviewId) {
        reviewService.delete(reviewId);
        return SuccessRes.of(REVIEW_DELETE);
    }
}
