package com.example.wanted_preonboarding_challenge_backend_31.application.review;

import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query.ReviewQueryRepository;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductRatingDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewComplexQueryService {

    private final ReviewQueryRepository reviewQueryRepository;

    public ProductRatingDetailDto getProductRatingDetail(Long productId) {
        return reviewQueryRepository.getProductRatingDetail(productId);
    }
}
