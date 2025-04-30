package com.example.wanted_preonboarding_challenge_backend_31.application.review;

import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query.ReviewQueryRepository;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationRes;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductRatingDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.review.ProductReviewSearchDataDto;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductReviewSearchReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductReviewSearchRes;
import java.util.List;
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

    public ProductReviewSearchRes getProductReviewSearchWithoutSummary(Long productId, PaginationReq paginationReq,
                                                                       ProductReviewSearchReq req) {
        List<ProductReviewSearchDataDto> reviewSearchData = reviewQueryRepository.getSearchProductReviews(productId,
                paginationReq, req);
        PaginationRes paginationRes = reviewQueryRepository.countSearchProductReviews(productId, paginationReq, req);

        return new ProductReviewSearchRes(reviewSearchData, null, paginationRes);
    }
}
