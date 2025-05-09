package com.preonboarding.challenge.service.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ProductDto {

    @Data
    @Builder
    public static class Product {
        private Long id;
        private String name;
        private String slug;
        private String shortDescription;
        private String fullDescription;
        private Seller seller;
        private Brand brand;
        private String status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Detail detail;
        private Price price;
        private List<Category> categories;
        private List<OptionGroup> optionGroups;
        private List<Image> images;
        private List<Tag> tags;
        private RatingSummary rating;
        private List<ProductSummary> relatedProducts;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductSummary {
        private Long id;
        private String name;
        private String slug;
        private String shortDescription;
        private BigDecimal basePrice;
        private BigDecimal salePrice;
        private String currency;
        private Image primaryImage;
        private Brand brand;
        private Seller seller;
        private Double rating;
        private Integer reviewCount;
        private boolean inStock;
        private String status;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    public static class Seller {
        private Long id;
        private String name;
    }


    @Data
    @Builder
    public static class Brand {
        private Long id;
        private String name;
    }

    @Data
    @Builder
    public static class Detail {
        private Double weight;
        private Map<String, Object> dimensions; // JSON: {"width": float, "height": float, "depth": float}
        private String materials;
        private String countryOfOrigin;
        private String warrantyInfo;
        private String careInstructions;
        private Map<String, Object> additionalInfo; // JSON object for additional information
    }

    @Data
    @Builder
    public static class Price {
        private BigDecimal basePrice;
        private BigDecimal salePrice;
        private BigDecimal costPrice;
        private String currency;
        private BigDecimal taxRate;
        private Integer discountPercentage = 0;
    }

    @Data
    @Builder
    public static class Category {
        private Long id;
        private String name;
        private String slug;
        private ParentCategory parent;
    }

    @Data
    @Builder
    public static class ParentCategory {
        private Long id;
        private String name;
        private String slug;
    }

    @Data
    @Builder
    public static class OptionGroup {
        private Long id;
        private String name;
        private Integer displayOrder;
        private List<Option> options;
    }

    @Data
    @Builder
    public static class Option {
        private Long id;
        private Long optionGroupId; // 명시적으로 추가
        private String name;
        private BigDecimal additionalPrice;
        private String sku;
        private Integer stock;
        private Integer displayOrder;
    }

    @Data
    @Builder
    public static class Image {
        private Long id;
        private String url;
        private String altText;
        private boolean isPrimary;
        private Integer displayOrder;
        private Long optionId;
    }

    @Data
    @Builder
    public static class Tag {
        private Long id;
        private String name;
        private String slug;
    }

    @Data
    @Builder
    public static class RatingSummary {
        private Double average;
        private Integer count;
        private Map<Integer, Integer> distribution; // Map rating -> count (e.g., {5: 95, 4: 20, ...})
    }


    @Data
    @Builder
    public static class ProductCategory {
        private Long categoryId;
        private Boolean isPrimary;
    }
}