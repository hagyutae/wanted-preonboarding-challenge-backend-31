package com.wanted.ecommerce.product.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.ecommerce.brand.dto.response.BrandDetailResponse;
import com.wanted.ecommerce.category.dto.response.CategoryResponse;
import com.wanted.ecommerce.common.dto.response.ProductItemResponse.ProductImageResponse;
import com.wanted.ecommerce.product.domain.Dimensions;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductDetail;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.domain.ProductPrice;
import com.wanted.ecommerce.review.dto.response.RatingResponse;
import com.wanted.ecommerce.seller.dto.response.SellerDetailResponse;
import com.wanted.ecommerce.tag.dto.response.TagResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductResponse(
    Long id,
    String name,
    String slug,
    String shortDescription,
    String fullDescription,
    SellerDetailResponse seller,
    BrandDetailResponse brand,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    DetailResponse detail,
    ProductPriceResponse price,
    List<CategoryResponse> categories,
    List<ProductOptionGroupResponse> optionGroups,
    List<ProductImageCreateResponse> images,
    List<TagResponse> tags,
    RatingResponse rating,
    List<RelatedProductResponse> relatedProducts

) {

    public static ProductResponse of(Product product, SellerDetailResponse seller,
        BrandDetailResponse brand, DetailResponse detail, ProductPriceResponse price,
        List<CategoryResponse> categories, List<ProductOptionGroupResponse> optionGroups,
        List<ProductImageCreateResponse> images, List<TagResponse> tags, RatingResponse rating,
        List<RelatedProductResponse> relatedProducts) {
        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .slug(product.getSlug())
            .shortDescription(product.getShortDescription())
            .fullDescription(product.getFullDescription())
            .seller(seller)
            .brand(brand)
            .status(product.getStatus().getName())
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getUpdatedAt())
            .detail(detail)
            .price(price)
            .categories(categories)
            .optionGroups(optionGroups)
            .images(images)
            .tags(tags)
            .rating(rating)
            .relatedProducts(relatedProducts)
            .build();
    }

    @Builder
    public record DetailResponse(
        Double weight,
        DimensionsResponse dimensions,
        String materials,
        String countryOfOrigin,
        String warrantyInfo,
        String careInstructions,
        Map<String, Object> additionalInfo
    ) {

        public static DetailResponse of(Double weight, DimensionsResponse dimensions,
            ProductDetail detail) {
            return DetailResponse.builder()
                .weight(weight)
                .dimensions(dimensions)
                .materials(detail.getMaterials())
                .countryOfOrigin(detail.getCountryOfOrigin())
                .warrantyInfo(detail.getWarrantyInfo())
                .careInstructions(detail.getCareInstructions())
                .additionalInfo(detail.getAdditionalInfo())
                .build();
        }
    }

    @Builder
    public record DimensionsResponse(
        Integer width,
        Integer height,
        Integer depth
    ) {

        public static DimensionsResponse of(Dimensions dimensions) {
            return DimensionsResponse.builder()
                .width(dimensions.getWidth())
                .height(dimensions.getHeight())
                .depth(dimensions.getDepth())
                .build();
        }
    }

    @Builder
    public record RelatedProductResponse(
        Long id,
        String name,
        String slug,
        String shortDescription,
        ProductImageResponse primaryImage,
        Integer basePrice,
        Integer salePrice,
        String currency
    ) {

        public static RelatedProductResponse of(Product product, ProductPrice price, ProductImageResponse image) {
            return RelatedProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .primaryImage(image)
                .basePrice(price.getBasePrice().intValue())
                .salePrice(price.getSalePrice().intValue())
                .currency(price.getCurrency())
                .build();
        }
    }

    @Builder
    public record ProductImageCreateResponse(
        Long id,
        String url,
        String altText,
        Boolean isPrimary,
        Integer displayOrder,
        Long optionId
    ) {

        public static ProductImageCreateResponse of(ProductImage image) {
            return ProductImageCreateResponse.builder()
                .id(image.getId())
                .url(image.getUrl())
                .altText(image.getAltText())
                .isPrimary(image.isPrimary())
                .displayOrder(image.getDisplayOrder())
                .build();
        }

        public static ProductImageCreateResponse of(ProductImage image, ProductOption option) {
            return ProductImageCreateResponse.builder()
                .id(image.getId())
                .url(image.getUrl())
                .altText(image.getAltText())
                .isPrimary(image.isPrimary())
                .displayOrder(image.getDisplayOrder())
                .optionId(option.getId())
                .build();
        }
    }

    @Builder
    public record ProductOptionGroupResponse(
        Long id,
        String name,
        Integer displayOrder,
        List<ProductOptionResponse> options
    ) {

        public static ProductOptionGroupResponse of(ProductOptionGroup optionGroup, List<ProductOptionResponse> options) {
            return ProductOptionGroupResponse.builder()
                .id(optionGroup.getId())
                .name(optionGroup.getName())
                .displayOrder(optionGroup.getDisplayOrder())
                .options(options)
                .build();
        }
    }

    @Builder
    public record ProductPriceResponse(
        Double basePrice,
        Double salePrice,
        String currency,
        Double taxRate,
        Double discountPercentage
    ) {

        public static ProductPriceResponse of(ProductPrice price) {
            double basePrice = Double.parseDouble(String.format("%.2f", price.getBasePrice()));
            double salePrice = Double.parseDouble(String.format("%.2f", price.getSalePrice()));
            double discount = basePrice - salePrice;
            double discountPercentage = Double.parseDouble(
                String.format("%.2f", (discount / basePrice) * 100));
            return ProductPriceResponse.builder()
                .basePrice(basePrice)
                .salePrice(salePrice)
                .currency(price.getCurrency())
                .taxRate(price.getTaxRate().doubleValue())
                .discountPercentage(discountPercentage)
                .build();
        }
    }
}
