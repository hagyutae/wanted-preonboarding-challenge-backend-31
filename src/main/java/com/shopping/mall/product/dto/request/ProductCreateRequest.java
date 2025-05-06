package com.shopping.mall.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    private String status;

    private Detail detail;
    private Price price;

    private List<CategoryInfo> categories;

    @Getter
    @NoArgsConstructor
    public static class Detail {
        private Double weight;
        private Map<String, Object> dimensions;
        private String materials;
        private String countryOfOrigin;
        private String warrantyInfo;
        private String careInstructions;
        private Map<String, Object> additionalInfo;
    }

    @Getter
    @NoArgsConstructor
    public static class Price {
        private Integer basePrice;
        private Integer salePrice;
        private Integer costPrice;
        private String currency;
        private Integer taxRate;
    }

    @Getter
    @NoArgsConstructor
    public static class CategoryInfo {
        private Long categoryId;
        private Boolean isPrimary;
    }
}
