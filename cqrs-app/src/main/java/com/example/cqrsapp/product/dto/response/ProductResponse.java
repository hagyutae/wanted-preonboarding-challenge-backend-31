package com.example.cqrsapp.product.dto.response;


import com.example.cqrsapp.common.dto.ReviewRatingDto;
import com.example.cqrsapp.product.domain.*;
import com.example.cqrsapp.seller.domain.Seller;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductResponse {
    private long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private SellerDto seller;
    private BrandDto brand;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductDetailDto detail;
    private ProductPriceDto price;
    private List<CategoryDto> categories;
    private List<OptionGroupDto> optionGroups;
    private List<ProductImageDto> images;
    private List<TagDto> tags;
    private ReviewRatingDto rating;

    public static ProductResponse fromEntity(Product product, ReviewRatingDto rating, Map<String, Long> distribution, List<Product> relatedProducts) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .status(product.getStatus().name())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .seller(SellerDto.fromEntity(product.getSeller()))
                .brand(BrandDto.fromEntity(product.getBrand()))
                .detail(ProductDetailDto.fromEntity(product.getProductDetail()))
                .price(ProductPriceDto.fromEntity(product.getProductPrice()))
                .categories(product.getProductCategory().stream()
                        .map(ProductCategory::getCategory)
                        .map(CategoryDto::fromEntity)
                        .toList())
                .optionGroups(product.getProductOptionGroups().stream()
                        .map(OptionGroupDto::fromEntity)
                        .toList())
                .images(product.getProductImages().stream()
                        .map(ProductImageDto::fromEntity)
                        .toList())
                .tags(product.getProductTags().stream()
                        .map(ProductTag::getTag)
                        .map(TagDto::fromEntity)
                        .toList())
                .rating(ReviewRatingDto.of(rating.getAverage(), rating.getCount(), distribution))
                .build();
    }


    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SellerDto {
        private long id;
        private String name;
        private String description;
        private String logoURL;
        private double rating;
        private String contactEmail;
        private String contactPhone;

        public static SellerDto fromEntity(Seller seller) {
            return SellerDto.builder()
                    .id(seller.getId())
                    .name(seller.getName())
                    .description(seller.getDescription())
                    .logoURL(seller.getLogoUrl())
                    .rating(seller.getRating().doubleValue())
                    .contactEmail(seller.getContactEmail())
                    .contactPhone(seller.getContactPhone())
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class BrandDto {
        private long id;
        private String name;
        private String description;
        private String logoURL;
        private String website;

        public static BrandDto fromEntity(Brand brand) {
            return BrandDto.builder()
                    .id(brand.getId())
                    .name(brand.getName())
                    .description(brand.getDescription())
                    .logoURL(brand.getLogoUrl())
                    .website(brand.getWebsite())
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ProductDetailDto {
        private DimensionsDto dimensions;
        private String materials;
        private String countryOfOrigin;
        private String warrantyInfo;
        private String careInstructions;
        private Map<String, String> additionalInfo;

        public static ProductDetailDto fromEntity(ProductDetail productDetail) {
            return ProductDetailDto.builder()
                    .dimensions(DimensionsDto.fromEntity(productDetail.getDimensions()))
                    .materials(productDetail.getMaterials())
                    .countryOfOrigin(productDetail.getCountryOfOrigin())
                    .warrantyInfo(productDetail.getWarrantyInfo())
                    .careInstructions(productDetail.getCareInstructions())
                    .additionalInfo(new LinkedHashMap<>(productDetail.getAdditionalInfo()))
                    .build();

        }
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DimensionsDto {
        private long width;
        private long height;
        private long depth;

        public static DimensionsDto fromEntity(Dimensions dimensions) {
            return DimensionsDto.builder()
                    .width(dimensions.getWidth())
                    .height(dimensions.getHeight())
                    .depth(dimensions.getDepth())
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ProductPriceDto {
        private long basePrice;
        private long salePrice;
        private String currency;
        private long taxRate;
        private long discountPercentage;

        public static ProductPriceDto fromEntity(ProductPrice productPrice) {
            BigDecimal discount = BigDecimal.valueOf(100)
                    .subtract(productPrice.getSalePrice()
                            .divide(productPrice.getBasePrice(), 2, RoundingMode.DOWN)
                            .multiply(BigDecimal.valueOf(100)));

            return ProductPriceDto.builder()
                    .basePrice(productPrice.getBasePrice().longValue())
                    .salePrice(productPrice.getSalePrice().longValue())
                    .currency(productPrice.getCurrency())
                    .taxRate(productPrice.getTaxRate().longValue())
                    .discountPercentage(discount.longValue())
                    .build();
        }
    }


    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CategoryDto {
        private long id;
        private String name;
        private String slug;
        private boolean isPrimary;
        private CategoryParentResponse parent;

        public static CategoryDto fromEntity(Category category) {
            return CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .slug(category.getSlug())
                    .isPrimary(category.getLevel() == 1)
                    .parent(category.getParent() != null ?
                            CategoryParentResponse.fromCategoryParent(category.getParent()) : null)
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CategoryParentResponse {
        private long id;
        private String name;
        private String slug;

        public static CategoryParentResponse fromCategoryParent(Category parent) {
            return CategoryParentResponse.builder()
                    .id(parent.getId())
                    .name(parent.getName())
                    .slug(parent.getSlug())
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class OptionGroupDto {
        private long id;
        private String name;
        private long displayOrder;
        private List<OptionResponse> options;

        private static OptionGroupDto fromEntity(ProductOptionGroup optionGroup) {
            return OptionGroupDto.builder()
                    .id(optionGroup.getId())
                    .name(optionGroup.getName())
                    .displayOrder(optionGroup.getDisplayOrder())
                    .options(optionGroup.getProductOptions().stream()
                            .map(OptionResponse::fromEntity)
                            .toList())
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class OptionResponse {
        private long id;
        private String name;
        private long additionalPrice;
        private String sku;
        private long stock;
        private long displayOrder;

        public static OptionResponse fromEntity(ProductOption productOption) {
            return OptionResponse.builder()
                    .id(productOption.getId())
                    .name(productOption.getName())
                    .additionalPrice(productOption.getAdditionalPrice().longValue())
                    .sku(productOption.getSku())
                    .stock(productOption.getStock())
                    .displayOrder(productOption.getDisplayOrder())
                    .build();
        }
    }


    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ProductImageDto {
        private long id;
        private String url;
        private String altText;
        private boolean isPrimary;
        private long displayOrder;
        private Long optionID;

        public static ProductImageDto fromEntity(ProductImage productImage) {
            return ProductImageDto.builder()
                    .id(productImage.getId())
                    .url(productImage.getUrl())
                    .altText(productImage.getAltText())
                    .isPrimary(productImage.getIsPrimary())
                    .displayOrder(productImage.getDisplayOrder())
                    .optionID(productImage.getOptionId())
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class TagDto {
        private long id;
        private String name;
        private String slug;

        public static TagDto fromEntity(Tag tag) {
            return TagDto.builder()
                    .id(tag.getId())
                    .name(tag.getName())
                    .slug(tag.getSlug())
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RelatedProductDto {
        private long id;
        private String name;
        private String slug;
        private String shortDescription;
        private PrimaryImageDto primaryImage;
        private long basePrice;
        private long salePrice;
        private String currency;

        public static RelatedProductDto fromEntity(Product product) {
            return RelatedProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .slug(product.getSlug())
                    .shortDescription(product.getShortDescription())
                    .primaryImage(extractPrimaryImage(product))
                    .basePrice(toLong(product.getProductPrice().getBasePrice()))
                    .salePrice(toLong(product.getProductPrice().getSalePrice()))
                    .currency(product.getProductPrice().getCurrency())
                    .build();
        }

        private static PrimaryImageDto extractPrimaryImage(Product product) {
            return product.getProductImages().stream()
                    .filter(ProductImage::getIsPrimary)
                    .findFirst()
                    .map(PrimaryImageDto::fromEntity)
                    .orElse(null); // 없을 경우 null 처리
        }

        private static long toLong(BigDecimal value) {
            return value != null ? value.longValue() : 0L;
        }
    }

    @Getter
    @Builder
    public static class PrimaryImageDto {
        private String url;
        private String altText;

        public static PrimaryImageDto fromEntity(ProductImage productImage) {
            return PrimaryImageDto.builder()
                    .url(productImage.getUrl())
                    .altText(productImage.getAltText())
                    .build();
        }
    }
}