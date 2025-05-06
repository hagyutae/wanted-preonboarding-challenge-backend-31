package minseok.cqrschallenge.product.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import minseok.cqrschallenge.product.entity.Dimensions;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private SellerDetail seller;
    private BrandDetail brand;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductDetailInfo detail;
    private ProductPriceInfo price;
    private List<CategoryInfo> categories;
    private List<OptionGroupInfo> optionGroups;
    private List<ProductImageInfo> images;
    private List<TagInfo> tags;
    private RatingInfo rating;
    private List<RelatedProductInfo> relatedProducts;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SellerDetail {
        private Long id;
        private String name;
        private String description;
        private String logoUrl;
        private BigDecimal rating;
        private String contactEmail;
        private String contactPhone;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandDetail {
        private Long id;
        private String name;
        private String description;
        private String logoUrl;
        private String website;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDetailInfo {
        private Double weight;
        private Dimensions dimensions;
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
    public static class ProductPriceInfo {
        private BigDecimal basePrice;
        private BigDecimal salePrice;
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
        private Boolean isPrimary;
        private CategoryParentInfo parent;
        
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CategoryParentInfo {
            private Long id;
            private String name;
            private String slug;
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionGroupInfo {
        private Long id;
        private String name;
        private Integer displayOrder;
        private List<OptionInfo> options;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class OptionInfo {
            private Long id;
            private String name;
            private BigDecimal additionalPrice;
            private String sku;
            private Integer stock;
            private Integer displayOrder;
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductImageInfo {
        private Long id;
        private String url;
        private String altText;
        private Boolean isPrimary;
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
    public static class RatingInfo {
        private Double average;
        private Integer count;
        private Map<String, Integer> distribution;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RelatedProductInfo {
        private Long id;
        private String name;
        private String slug;
        private String shortDescription;
        private ProductImageSummary primaryImage;
        private BigDecimal basePrice;
        private BigDecimal salePrice;
        private String currency;
        
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ProductImageSummary {
            private String url;
            private String altText;
        }
    }
}