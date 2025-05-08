package com.example.demo.product.dto;

import com.example.demo.brand.entity.BrandEntity;
import com.example.demo.product.entity.*;
import com.example.demo.productoption.entity.ProductOptionEntity;
import com.example.demo.productoption.entity.ProductOptionGroupEntity;
import com.example.demo.review.dto.ReviewDistribution;
import com.example.demo.user.entity.SellerEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductDetail(
        Long id,
        String name,
        String slug,
        String shortDescription,
        String fullDescription,
        SellerDetail seller,
        BrandDetail brand,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Detail detail,
        PriceDetail price,
        List<CategoryDetail> categories,
        List<OptionGroupDetail> optionGroups,
        List<ImageDetail> images,
        List<TagDetail> tags,
        RatingDetail rating,
        List<RelatedProduct> relatedProducts
) {
    public static ProductDetail of(
            ProductEntity productEntity,
            List<ProductCategoryEntity> productCategoryEntityList,
            Map<Long, List<ProductImageEntity>> productImageEntityMap,
            List<ProductOptionGroupEntity> productOptionGroupEntityList,
            Map<Long, List<ProductOptionEntity>> productOptionEntityMap,
            List<ProductTagEntity> productTagEntityList,
            ReviewDistribution reviewDistribution,
            Map<Long, ProductEntity> relatedProductMap
    ) {
        return new ProductDetail(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getSlug(),
                productEntity.getShortDescription(),
                productEntity.getFullDescription(),
                SellerDetail.of(productEntity.getSellerEntity()),
                BrandDetail.of(productEntity.getBrandEntity()),
                productEntity.getStatus().name(),
                productEntity.getCreatedAt(),
                productEntity.getUpdatedAt(),
                Detail.of(productEntity.getProductDetailEntity()),
                PriceDetail.of(productEntity.getProductPriceEntity()),
                productCategoryEntityList.stream()
                        .map(CategoryDetail::of)
                        .toList(),
                productOptionGroupEntityList.stream()
                        .map(productOptionGroupEntity -> OptionGroupDetail.of(productOptionGroupEntity, productOptionEntityMap.get(productOptionGroupEntity.getId())))
                        .toList(),
                productImageEntityMap.get(productEntity.getId()).stream()
                        .map(ImageDetail::of)
                        .toList(),
                productTagEntityList.stream()
                        .map(TagDetail::of)
                        .toList(),
                RatingDetail.of(reviewDistribution),
                relatedProductMap.keySet().stream()
                        .map(productId -> RelatedProduct.of(relatedProductMap.get(productId), productImageEntityMap.get(productId)))
                        .toList()
        );
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record SellerDetail(
            Long id,
            String name,
            String description,
            String logoUrl,
            BigDecimal rating,
            String contactEmail,
            String contactPhone
    ) {
        public static SellerDetail of(SellerEntity sellerEntity) {
            return new SellerDetail(
                    sellerEntity.getId(),
                    sellerEntity.getName(),
                    sellerEntity.getDescription(),
                    sellerEntity.getLogoUrl(),
                    sellerEntity.getRating().setScale(2, RoundingMode.HALF_UP),
                    sellerEntity.getContactEmail(),
                    sellerEntity.getContactPhone()
            );
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record BrandDetail(
            Long id,
            String name,
            String description,
            String logoUrl,
            String website
    ) {
        public static BrandDetail of(BrandEntity brandEntity) {
            return new BrandDetail(
                    brandEntity.getId(),
                    brandEntity.getName(),
                    brandEntity.getDescription(),
                    brandEntity.getLogoUrl(),
                    brandEntity.getWebsite()
            );
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Detail(
            BigDecimal weight,
            Map<String, Object> dimensions,
            String materials,
            String countryOfOrigin,
            String warrantyInfo,
            String careInstructions,
            Map<String, Object> additionalInfo
    ) {
        public static Detail of(ProductDetailEntity productDetailEntity) {
            return new Detail(
                    productDetailEntity.getWeight().setScale(2, RoundingMode.HALF_UP),
                    productDetailEntity.getDimensions(),
                    productDetailEntity.getMaterials(),
                    productDetailEntity.getCountryOfOrigin(),
                    productDetailEntity.getWarrantyInfo(),
                    productDetailEntity.getCareInstructions(),
                    productDetailEntity.getAdditionalInfo()
            );
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record PriceDetail(
            BigDecimal basePrice,
            BigDecimal salePrice,
            String currency,
            BigDecimal taxRate,
            BigDecimal discountPercentage
    ) {
        public static PriceDetail of(ProductPriceEntity productPriceEntity) {
            return new PriceDetail(
                    productPriceEntity.getBasePrice().setScale(2, RoundingMode.HALF_UP),
                    productPriceEntity.getSalePrice().setScale(2, RoundingMode.HALF_UP),
                    productPriceEntity.getCurrency(),
                    productPriceEntity.getTaxRate().setScale(2, RoundingMode.HALF_UP),
                    productPriceEntity.getBasePrice().subtract(productPriceEntity.getSalePrice())
                            .divide(productPriceEntity.getBasePrice(), 2, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .setScale(2, RoundingMode.HALF_UP)
            );
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record CategoryDetail(
            Long id,
            String name,
            String slug,
            Boolean isPrimary,
            ParentCategory parent
    ) {
        public static CategoryDetail of(ProductCategoryEntity productCategoryEntity) {
            return new CategoryDetail(
                    productCategoryEntity.getCategoryEntity().getId(),
                    productCategoryEntity.getCategoryEntity().getName(),
                    productCategoryEntity.getCategoryEntity().getSlug(),
                    productCategoryEntity.getIsPrimary(),
                    new ParentCategory(
                            Objects.nonNull(productCategoryEntity.getCategoryEntity().getParent())
                                    ? productCategoryEntity.getCategoryEntity().getParent().getId() : null,
                            Objects.nonNull(productCategoryEntity.getCategoryEntity().getParent())
                                    ? productCategoryEntity.getCategoryEntity().getParent().getName() : null,
                            Objects.nonNull(productCategoryEntity.getCategoryEntity().getParent())
                                    ? productCategoryEntity.getCategoryEntity().getParent().getSlug() : null
                    )
            );
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record ParentCategory(
            Long id,
            String name,
            String slug
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record OptionGroupDetail(
            Long id,
            String name,
            Integer displayOrder,
            List<OptionDetail> options
    ) {
        public static OptionGroupDetail of(
                ProductOptionGroupEntity productOptionGroupEntity,
                List<ProductOptionEntity> productOptionEntityList
        ) {
            return new OptionGroupDetail(
                    productOptionGroupEntity.getId(),
                    productOptionGroupEntity.getName(),
                    productOptionGroupEntity.getDisplayOrder(),
                    productOptionEntityList.stream()
                            .map(OptionDetail::of)
                            .toList()
            );
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record OptionDetail(
            Long id,
            String name,
            BigDecimal additionalPrice,
            String sku,
            Integer stock,
            Integer displayOrder
    ) {
        public static OptionDetail of(ProductOptionEntity productOptionEntity) {
            return new OptionDetail(
                    productOptionEntity.getId(),
                    productOptionEntity.getName(),
                    productOptionEntity.getAdditionalPrice().setScale(2, RoundingMode.HALF_UP),
                    productOptionEntity.getSku(),
                    productOptionEntity.getStock(),
                    productOptionEntity.getDisplayOrder()
            );
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record ImageDetail(
            Long id,
            String url,
            String altText,
            Boolean isPrimary,
            Integer displayOrder,
            Long optionId
    ) {
        public static ImageDetail of(ProductImageEntity productImageEntity) {
            return new ImageDetail(
                    productImageEntity.getId(),
                    productImageEntity.getUrl(),
                    productImageEntity.getAltText(),
                    productImageEntity.getIsPrimary(),
                    productImageEntity.getDisplayOrder(),
                    productImageEntity.getOptionId()
            );
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record TagDetail(
            Long id,
            String name,
            String slug
    ) {
        public static TagDetail of(ProductTagEntity productTagEntity) {
            return new TagDetail(
                    productTagEntity.getTagEntity().getId(),
                    productTagEntity.getTagEntity().getName(),
                    productTagEntity.getTagEntity().getSlug()
            );
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record RatingDetail(
            Double average,
            Long count,
            RatingDistribution distribution
    ) {
        public static RatingDetail of(ReviewDistribution reviewDistribution) {
            return new RatingDetail(
                    reviewDistribution.getAverage(),
                    reviewDistribution.getTotal(),
                    new RatingDistribution(
                            Map.of("5", reviewDistribution.five(),
                                    "4", reviewDistribution.four(),
                                    "3", reviewDistribution.three(),
                                    "2", reviewDistribution.two(),
                                    "1", reviewDistribution.one())
                    )
            );
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record RatingDistribution(
            Map<String, Long> ratings
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record RelatedProduct(
            Long id,
            String name,
            String slug,
            String shortDescription,
            PrimaryImage primaryImage,
            BigDecimal basePrice,
            BigDecimal salePrice,
            String currency
    ) {
        public static RelatedProduct of(ProductEntity productEntity, List<ProductImageEntity> productImageEntities) {
            Optional<ProductImageEntity> primaryImage = productImageEntities.stream().filter(ProductImageEntity::getIsPrimary)
                    .findFirst();
            return new RelatedProduct(
                    productEntity.getId(),
                    productEntity.getName(),
                    productEntity.getSlug(),
                    productEntity.getShortDescription(),
                    primaryImage.map(productImageEntity -> new PrimaryImage(productImageEntity.getUrl(), productImageEntity.getAltText()))
                            .orElseGet(() -> new PrimaryImage(null, null)),
                    productEntity.getProductPriceEntity().getBasePrice(),
                    productEntity.getProductPriceEntity().getSalePrice(),
                    productEntity.getProductPriceEntity().getCurrency()
            );
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record PrimaryImage(
            String url,
            String altText
    ) {
    }
}
