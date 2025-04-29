package com.psh10066.commerce.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record CreateProductRequest(
    @NotBlank(message = "상품명은 필수 항목입니다.")
    String name,

    @NotBlank(message = "슬러그는 필수 항목입니다.")
    String slug,

    @Size(max = 500, message = "짧은 설명은 500자를 초과할 수 없습니다.")
    String shortDescription,

    String fullDescription,

    @NotNull(message = "판매자 ID는 필수 항목입니다.")
    Long sellerId,

    @NotNull(message = "브랜드 ID는 필수 항목입니다.")
    Long brandId,

    @NotBlank(message = "상태는 필수 항목입니다.")
    String status,

    @Valid
    ProductDetailRequest detail,

    @Valid
    @NotNull(message = "가격 정보는 필수 항목입니다.")
    ProductPriceRequest price,

    @Valid
    @Size(min = 1, message = "최소 하나의 카테고리를 지정해야 합니다.")
    List<ProductCategoryRequest> categories,

    @Valid
    List<ProductOptionGroupRequest> optionGroups,

    @Valid
    List<ProductImageRequest> images,

    List<Long> tags
) {

    public record ProductDetailRequest(
        BigDecimal weight,
        ProductDetailDimensionRequest dimensions,
        String materials,
        String countryOfOrigin,
        String warrantyInfo,
        String careInstructions,
        Map<String, Object> additionalInfo
    ) {}

    public record ProductDetailDimensionRequest(
        Integer width,
        Integer height,
        Integer depth
    ) {}

    public record ProductPriceRequest(
        @NotNull(message = "기본 가격은 필수 항목입니다.")
        BigDecimal basePrice,

        BigDecimal salePrice,
        BigDecimal costPrice,
        String currency,
        BigDecimal taxRate
    ) {
        // Constructor with default values
        public ProductPriceRequest {
            if (currency == null) currency = "KRW";
        }
    }

    public record ProductCategoryRequest(
        @NotNull(message = "카테고리 ID는 필수 항목입니다.")
        Long categoryId,

        Boolean isPrimary
    ) {
        // Constructor with default values
        public ProductCategoryRequest {
            if (isPrimary == null) isPrimary = false;
        }
    }

    public record ProductOptionGroupRequest(
        @NotBlank(message = "옵션 그룹명은 필수 항목입니다.")
        String name,

        Integer displayOrder,

        @Valid
        @Size(min = 1, message = "최소 하나의 옵션을 지정해야 합니다.")
        List<ProductOptionRequest> options
    ) {
        // Constructor with default values
        public ProductOptionGroupRequest {
            if (displayOrder == null) displayOrder = 0;
        }
    }

    public record ProductOptionRequest(
        @NotBlank(message = "옵션명은 필수 항목입니다.")
        String name,

        BigDecimal additionalPrice,
        String sku,
        Integer stock,
        Integer displayOrder
    ) {
        // Constructor with default values
        public ProductOptionRequest {
            if (additionalPrice == null) additionalPrice = BigDecimal.ZERO;
            if (stock == null) stock = 0;
            if (displayOrder == null) displayOrder = 0;
        }
    }

    public record ProductImageRequest(
        @NotBlank(message = "이미지 URL은 필수 항목입니다.")
        String url,

        String altText,
        Boolean isPrimary,
        Integer displayOrder,
        Long optionId
    ) {
        // Constructor with default values
        public ProductImageRequest {
            if (isPrimary == null) isPrimary = false;
            if (displayOrder == null) displayOrder = 0;
        }
    }
}
