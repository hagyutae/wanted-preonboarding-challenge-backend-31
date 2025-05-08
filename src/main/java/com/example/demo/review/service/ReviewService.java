package com.example.demo.review.service;

import com.example.demo.common.exception.ErrorCode;
import com.example.demo.common.exception.GlobalException;
import com.example.demo.product.entity.ProductEntity;
import com.example.demo.product.repository.ProductRepository;
import com.example.demo.review.ReviewError;
import com.example.demo.review.controller.request.AddReviewRequest;
import com.example.demo.review.controller.request.UpdateReviewRequest;
import com.example.demo.review.dto.*;
import com.example.demo.review.entity.ReviewEntity;
import com.example.demo.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ReviewStatistic> findStatisticsByProductIds(Collection<Long> productIds) {
        return reviewRepository.findAllByProductIds(productIds);
    }

    @Transactional(readOnly = true)
    public ReviewDistribution findDistributionByProductId(Long productId) {
        return reviewRepository.findDistributionByProductId(productId);
    }

    @Transactional(readOnly = true)
    public List<Long> findHotTop10ProductIds() {
        return reviewRepository.findHotTop10ProductIds();
    }

    public AddReviewResult addReview(Long productId, AddReviewRequest addReviewRequest) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ReviewError.REVIEW_ADD_FAIL));

        ReviewEntity reviewEntity = ReviewEntity.create(addReviewRequest, productEntity);

        ReviewEntity save = reviewRepository.save(reviewEntity);

        return AddReviewResult.of(save);
    }

    @Transactional(readOnly = true)
    public ReviewSummaryResponse findReviewSummaryPage(ReviewQueryFilter reviewQueryFilter, Pageable pageable) {
        ReviewDistribution reviewDistribution = reviewRepository.findDistributionByProductId(reviewQueryFilter.productId());
        Page<ReviewEntity> reviewEntityPage = reviewRepository.findPage(reviewQueryFilter, pageable);

        List<ReviewSummary> content = reviewEntityPage.getContent().stream()
                .map(ReviewSummary::of)
                .toList();
        Page<ReviewSummary> reviewSummaryPage = new PageImpl<>(content, pageable, reviewEntityPage.getTotalPages());

        return ReviewSummaryResponse.of(reviewSummaryPage, ReviewSummaryDistribution.of(reviewDistribution));
    }

    public UpdateReviewResult update(Long id, UpdateReviewRequest updateReviewRequest) {
        ReviewEntity reviewEntity = reviewRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ReviewError.UPDATE_FAIL));

        reviewEntity.update(updateReviewRequest);

        ReviewEntity save = reviewRepository.save(reviewEntity);

        return UpdateReviewResult.of(save);
    }

    public void deleteById(Long id) {
        ReviewEntity reviewEntity = reviewRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ReviewError.UPDATE_FAIL));
        reviewRepository.delete(reviewEntity);
    }
}
