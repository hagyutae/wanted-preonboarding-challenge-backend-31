package com.preonboarding.challenge.service.query.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDocument {

    @Id
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private String status; // ACTIVE, OUT_OF_STOCK, DELETED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 판매자 정보
    private SellerInfo seller;

    // 브랜드 정보
    private BrandInfo brand;

    // 상세 정보
    private ProductDetail detail;

    // 가격 정보
    private PriceInfo price;

    // 카테고리 목록
    @Builder.Default
    private List<CategoryInfo> categories = new ArrayList<>();

    // 옵션 그룹 목록
    @Builder.Default
    private List<OptionGroup> optionGroups = new ArrayList<>();

    // 이미지 목록
    @Builder.Default
    private List<Image> images = new ArrayList<>();

    // 태그 목록
    @Builder.Default
    private List<TagInfo> tags = new ArrayList<>();

    // 리뷰 요약 정보
    private RatingSummary rating;

    // 중첩 문서들의 정의
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SellerInfo {
        private Long id;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandInfo {
        private Long id;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDetail {
        private Double weight;
        private Map<String, Object> dimensions;
        private String materials;
        private String countryOfOrigin;
        private String warrantyInfo;
        private String careInstructions;
        private Map<String, Object> additionalInfo;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PriceInfo {
        private BigDecimal basePrice;
        private BigDecimal salePrice;
        private BigDecimal costPrice;
        private String currency;
        private BigDecimal taxRate;
        private Integer discountPercentage;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryInfo {
        private Long id;
        private String name;
        private String slug;
        private ParentCategory parent;
        private boolean isPrimary;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParentCategory {
        private Long id;
        private String name;
        private String slug;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionGroup {
        private Long id;
        private String name;
        private Integer displayOrder;
        @Builder.Default
        private List<Option> options = new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Option {
        private Long id;
        private String name;
        private BigDecimal additionalPrice;
        private String sku;
        private Integer stock;
        private Integer displayOrder;
        @Builder.Default
        private List<Image> images = new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TagInfo {
        private Long id;
        private String name;
        private String slug;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RatingSummary {
        private Double average;
        private Integer count;
        private Map<Integer, Integer> distribution; // Map rating -> count (e.g., {5: 95, 4: 20, ...})
    }
}