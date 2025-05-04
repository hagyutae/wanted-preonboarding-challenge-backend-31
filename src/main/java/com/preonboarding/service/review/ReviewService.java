package com.preonboarding.service.review;

import com.preonboarding.dto.request.product.ProductReviewRequestDto;
import com.preonboarding.dto.response.product.ProductReviewResponse;
import com.preonboarding.global.response.BaseResponse;

public interface ReviewService {
    BaseResponse<ProductReviewResponse> editReview(Long id,ProductReviewRequestDto dto);
    BaseResponse<ProductReviewResponse> deleteReview(Long id);
}
