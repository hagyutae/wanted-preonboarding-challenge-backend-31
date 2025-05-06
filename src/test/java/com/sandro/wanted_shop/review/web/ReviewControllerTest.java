package com.sandro.wanted_shop.review.web;

import com.sandro.wanted_shop.common.util.ObjectMapperUtil;
import com.sandro.wanted_shop.review.CreateReviewCommand;
import com.sandro.wanted_shop.config.IntegrationTestContext;
import com.sandro.wanted_shop.review.UpdateReviewCommand;
import com.sandro.wanted_shop.review.entity.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends IntegrationTestContext {
    @DisplayName("특정 상품에 대한 모든 리뷰를 조회한다.")
    @Test
    void getAllReviews() throws Exception {
        Long productId = productRepository.findAll().getFirst().getId();

        ResultActions resultActions = mvc.perform(get("/api/products/{id}/reviews", productId));

        resultActions
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.size()").value(4)
                )
                .andDo(print());
    }

    @DisplayName("리뷰를 등록한다.")
    @Test
    void createReview() throws Exception {
        Long productId = productRepository.findAll().getFirst().getId();
        Long userId = userRepository.findAll().getFirst().getId();

        CreateReviewCommand command = new CreateReviewCommand(
                userId, 5, "너무 좋아요!",
                "짱인듯..", true, 0
        );

        ResultActions resultActions = mvc.perform(
                post("/api/products/{id}/reviews", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectMapperUtil.writeValueAsString(command))
        );

        resultActions
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").exists()
                )
                .andDo(print());
    }

    @DisplayName("리뷰를 수정한다.")
    @Test
    void updateReview() throws Exception {
        long id = 1L;
        Review review = reviewRepository.findById(id).orElseThrow();

        UpdateReviewCommand updateReviewCommand = new UpdateReviewCommand(
                review.getRating(),
                review.getTitle(),
                review.getContent(),
                review.getVerifiedPurchase(),
                review.getHelpfulVotes() + 1
        );

        ResultActions resultActions = mvc.perform(
                put("/api/reviews/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ObjectMapperUtil.writeValueAsString(updateReviewCommand)));

        resultActions
                .andExpect(status().isOk())
                .andDo(print());

        Review updatedReview = reviewRepository.findById(id).orElseThrow();
        assertThat(updatedReview.getHelpfulVotes()).isEqualTo(review.getHelpfulVotes() + 1);
    }

    @DisplayName("리뷰를 삭제한다.")
    @Test
    void deleteReview() throws Exception {
        long id = 1L;

        ResultActions resultActions = mvc.perform(
                delete("/api/reviews/{id}", id));

        resultActions
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(reviewRepository.findById(id)).isEmpty();
    }
}