package com.wanted.ecommerce.product.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegisterRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    @NotBlank
    private String status;
    private ProductDetailRequest detail;
    private ProductPriceRequest price;
    private List<ProductCategoryRequest> categories;
    private List<ProductOptionGroupRequest> optionGroups;
    private List<ProductImageRequest> images;
    private List<Long> tags;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductImageRequest {
        @NotBlank
        private String url;
        private String altText;
        private Boolean isPrimary;
        private Integer displayOrder;
        private Long optionId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductOptionGroupRequest {
        private String name;
        private Integer displayOrder;
        private List<ProductOptionRequest> options;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductCategoryRequest {
        private Long categoryId;
        private Boolean isPrimary;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductPriceRequest {
        @Digits(integer = 10, fraction = 2)
        private BigDecimal basePrice;
        @Digits(integer = 10, fraction = 2)
        private BigDecimal salePrice;
        @Digits(integer = 10, fraction = 2)
        private BigDecimal costPrice;
        @Size(max = 3)
        private String currency;
        @Digits(integer = 3, fraction = 2)
        private BigDecimal taxRate;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDetailRequest {
        @Digits(integer = 8, fraction = 2)
        private BigDecimal weight;
        private DimensionsRequest dimensions;
        private String materials;
        @Size(max = 100)
        private String countryOfOrigin;
        private String warrantyInfo;
        private String careInstructions;
        private HashMap<String, Object> additionalInfo;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DimensionsRequest {

        private Integer width;
        private Integer height;
        private Integer depth;
    }
}
