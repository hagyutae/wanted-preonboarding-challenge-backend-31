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

    public record ProductPriceRequest(
            BigDecimal basePrice,
            BigDecimal salePrice,
            BigDecimal costPrice,
            String currency,
            BigDecimal taxRate
    ) {
    }

    public record ProductCategoryRequest(
            Long categoryId,
            boolean isPrimary
    ) {
    }

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

    public record ProductImageRequest(
            String url,
            String altText,
            boolean isPrimary,
            int displayOrder,
            Long optionId
    ) {
    }
}
