package com.example.wanted_preonboarding_challenge_backend_31.application.review;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.ErrorInfo;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CommonErrorType;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CustomException;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.review.Review;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public List<Long> getPopularProductIds(int limit) {
        return reviewRepository.findPopularProductIds(limit);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        ErrorInfo.of(CommonErrorType.RESOURCE_NOT_FOUND, "요청한 리뷰를 찾을 수 없습니다, ID:" + id)));
    }
}
