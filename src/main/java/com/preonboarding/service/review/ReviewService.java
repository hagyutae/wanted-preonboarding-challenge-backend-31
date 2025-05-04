package com.preonboarding.service.review;

import com.preonboarding.dto.request.review.ProductReviewRequestDto;
import com.preonboarding.dto.response.review.ProductReviewResponse;
import com.preonboarding.global.response.BaseResponse;

public interface ReviewService {
    BaseResponse<ProductReviewResponse> editReview(Long id,ProductReviewRequestDto dto);
    BaseResponse<ProductReviewResponse> deleteReview(Long id);
}
