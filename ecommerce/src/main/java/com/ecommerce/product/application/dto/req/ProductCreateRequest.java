package com.ecommerce.product.application.dto.req;

import java.math.BigDecimal;
import java.util.List;

public record ProductCreateRequest(
        String name,
        String slug,
        String shortDescription,
        String fullDescription,
        Long sellerId,
        Long brandId,
        String status,
        ProductDetailRequest detail,
        ProductPriceRequest price,
        List<ProductCategoryRequest> categories,
        List<ProductOptionGroupRequest> optionGroups,
        List<ProductImageRequest> images,
        List<Long> tagIds
) {
    // 상품 상세 정보 - JSON 구조와 일치
    public record ProductDetailRequest(
            Double weight,
            DimensionsRequest dimensions,
            String materials,
            String countryOfOrigin,
            String warrantyInfo,
            String careInstructions,
            AdditionalInfoRequest additionalInfoRequest
    ) {
        public record DimensionsRequest(
                int width,
                int height,
                int depth
        ) {
        }

        public record AdditionalInfoRequest(
                Boolean assemblyRequired,
                String assemblyTime
        ) {
        }
    }

    // 상품 가격 정보
    public record ProductPriceRequest(
            BigDecimal basePrice,
            BigDecimal salePrice,
            BigDecimal costPrice,
            String currency,
            BigDecimal taxRate
    ) {
    }

    // 상품 카테고리 정보
    public record ProductCategoryRequest(
            Long categoryId,
            boolean isPrimary
    ) {
    }

    // 상품 옵션 그룹 정보
    public record ProductOptionGroupRequest(
            String name,
            int displayOrder,
            List<ProductOptionRequest> options
    ) {
        public record ProductOptionRequest(
                String name,
                BigDecimal additionalPrice,
                String sku,
                int stock,
                int displayOrder
        ) {
        }
    }

    // 상품 이미지 정보
    public record ProductImageRequest(
            String url,
            String altText,
            boolean isPrimary,
            int displayOrder,
            Long optionId
    ) {
    }
}
