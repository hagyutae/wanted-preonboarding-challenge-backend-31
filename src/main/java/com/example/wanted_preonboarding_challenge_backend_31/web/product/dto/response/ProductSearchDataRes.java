package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductStatus;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query.dto.ProductReviewSummaryDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.brand.BrandSearchDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductImageSearchDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.seller.SellerSearchDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductSearchDataRes(
        Long id,
        String name,
        String slug,
        String shortDescription,
        BigDecimal basePrice,
        BigDecimal salePrice,
        String currency,
        ProductImageSearchDto primaryImage,
        BrandSearchDto brand,
        SellerSearchDto seller,
        Double rating,
        Long reviewCount,
        boolean inStock,
        ProductStatus status,
        LocalDateTime createdAt
) {

    public static ProductSearchDataRes withReviewSummary(ProductSearchDataRes res,
                                                         ProductReviewSummaryDto reviewSummary) {
        return new ProductSearchDataRes(
                res.id(),
                res.name(),
                res.slug(),
                res.shortDescription(),
                res.basePrice(),
                res.salePrice(),
                res.currency(),
                res.primaryImage(),
                res.brand(),
                res.seller(),
                reviewSummary.rating(),
                reviewSummary.reviewCount(),
                res.inStock(),
                res.status(),
                res.createdAt()
        );
    }
}
