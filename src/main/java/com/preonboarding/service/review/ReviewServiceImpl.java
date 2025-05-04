package com.preonboarding.service.review;

import com.preonboarding.domain.Review;
import com.preonboarding.domain.User;
import com.preonboarding.dto.request.product.ProductReviewRequestDto;
import com.preonboarding.dto.response.product.ProductReviewResponse;
import com.preonboarding.global.code.ErrorCode;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.global.response.ErrorResponseDto;
import com.preonboarding.repository.review.ReviewRepository;
import com.preonboarding.repository.user.UserRepository;
import com.preonboarding.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BaseResponse<ProductReviewResponse> editReview(Long id,ProductReviewRequestDto dto) {
        Long userId = JwtUtil.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(false, ErrorResponseDto.of(ErrorCode.USER_NOT_FOUND)));

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new BaseException(false,ErrorResponseDto.of(ErrorCode.REVIEW_NOT_FOUND)));
        review.validateUser(user);
        review.updateReview(dto);

        ProductReviewResponse response = ProductReviewResponse.builder()
                .id(user.getId())
                .rating(review.getRating())
                .title(review.getTitle())
                .content(review.getContent())
                .updatedAt(review.getUpdatedAt())
                .build();

        return BaseResponse.<ProductReviewResponse>builder()
                .success(true)
                .data(response)
                .message("리뷰가 성공적으로 수정되었습니다.")
                .build();
    }

    @Override
    @Transactional
    public BaseResponse<ProductReviewResponse> deleteReview(Long id) {
        Long userId = JwtUtil.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(false, ErrorResponseDto.of(ErrorCode.USER_NOT_FOUND)));

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new BaseException(false,ErrorResponseDto.of(ErrorCode.REVIEW_NOT_FOUND)));
        review.validateUser(user);
        reviewRepository.delete(review);

        return BaseResponse.<ProductReviewResponse>builder()
                .success(true)
                .data(null)
                .message("리뷰가 성공적으로 삭제되었습니다.")
                .build();
    }
}
