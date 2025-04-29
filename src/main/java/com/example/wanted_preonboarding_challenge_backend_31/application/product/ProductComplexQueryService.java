package com.example.wanted_preonboarding_challenge_backend_31.application.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.query.ProductQueryRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query.ReviewQueryRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query.dto.ProductReviewSummaryDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductSearchReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductDetailRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductSearchDataRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductSearchRes;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductComplexQueryService {

    private final ProductQueryRepository productQueryRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    public ProductSearchRes searchProducts(PaginationReq paginationReq, ProductSearchReq req) {
        List<ProductSearchDataRes> products = productQueryRepository.searchProducts(paginationReq, req);
        PaginationRes paginationRes = productQueryRepository.countSearchProducts(paginationReq, req);

        List<Long> productIds = products.stream()
                .map(ProductSearchDataRes::id)
                .toList();
        Map<Long, ProductReviewSummaryDto> reviewSummaries = reviewQueryRepository.getProductReviewSummaries(
                productIds);

        List<ProductSearchDataRes> mergedRes = products.stream()
                .map(res -> ProductSearchDataRes.withReviewSummary(res, reviewSummaries.get(res.id())))
                .toList();

        return new ProductSearchRes(mergedRes, paginationRes);
    }

    public ProductDetailRes detailProduct(Long productId) {
        return productQueryRepository.detailProduct(productId);
    }
}
