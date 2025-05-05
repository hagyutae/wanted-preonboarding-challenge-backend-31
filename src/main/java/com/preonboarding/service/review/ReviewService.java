package com.preonboarding.service.review;

import com.preonboarding.dto.request.review.ProductReviewRequestDto;
import com.preonboarding.dto.response.review.ProductReviewPageResponse;
import com.preonboarding.global.response.BaseResponse;

public interface ReviewService {
    BaseResponse<ProductReviewPageResponse> editReview(Long id, ProductReviewRequestDto dto);
    BaseResponse<ProductReviewPageResponse> deleteReview(Long id);
}
