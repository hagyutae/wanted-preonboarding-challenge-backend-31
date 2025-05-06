package com.sandro.wanted_shop.review;

import com.sandro.wanted_shop.config.IntegrationTestContext;
import com.sandro.wanted_shop.review.entity.Review;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewServiceTest extends IntegrationTestContext {
    @Test
    void createReview() throws Exception {
        long productId = 1L;

        CreateReviewCommand command = new CreateReviewCommand(
                1L, 5, "너무 좋아요!",
                "짱인듯..", true, 0
        );

        Long savedId = reviewService.create(productId, command);

        Review foundReview = reviewRepository.findById(savedId)
                .orElseThrow();

        assertThat(foundReview).isNotNull();
    }

}