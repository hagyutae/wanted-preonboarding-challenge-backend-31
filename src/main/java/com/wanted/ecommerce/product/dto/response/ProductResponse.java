package com.wanted.ecommerce.product.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.ecommerce.brand.dto.response.BrandDetailResponse;
import com.wanted.ecommerce.category.dto.response.CategoryResponse;
import com.wanted.ecommerce.review.dto.response.RatingResponse;
import com.wanted.ecommerce.seller.dto.response.SellerDetailResponse;
import com.wanted.ecommerce.tag.dto.response.TagResponse;
import java.math.BigDecimal;
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

    public static ProductResponse of(Long id, String name, String slug,
        String shortDescription, String fullDescription, SellerDetailResponse seller,
        BrandDetailResponse brand, String status, LocalDateTime createdAt, LocalDateTime updatedAt,
        DetailResponse detail, ProductPriceResponse price, List<CategoryResponse> categories,
        List<ProductOptionGroupResponse> optionGroups, List<ProductImageCreateResponse> images,
        List<TagResponse> tags, RatingResponse rating,
        List<RelatedProductResponse> relatedProducts) {
        return ProductResponse.builder()
            .id(id)
            .name(name)
            .slug(slug)
            .shortDescription(shortDescription)
            .fullDescription(fullDescription)
            .seller(seller)
            .brand(brand)
            .status(status)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
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
            String materials, String countryOfOrigin, String warrantyInfo, String careInstructions,
            Map<String, Object> additionalInfo) {
            return DetailResponse.builder()
                .weight(weight)
                .dimensions(dimensions)
                .materials(materials)
                .countryOfOrigin(countryOfOrigin)
                .warrantyInfo(warrantyInfo)
                .careInstructions(careInstructions)
                .additionalInfo(additionalInfo)
                .build();
        }
    }

    @Builder
    public record DimensionsResponse(
        Integer width,
        Integer height,
        Integer depth
    ) {

        public static DimensionsResponse of(Integer width, Integer height, Integer depth) {
            return DimensionsResponse.builder()
                .width(width)
                .height(height)
                .depth(depth)
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

        public static RelatedProductResponse of(Long id, String name, String slug,
            String shortDescription, ProductImageResponse primaryImage, BigDecimal basePrice,
            BigDecimal salePrice, String currency) {
            return RelatedProductResponse.builder()
                .id(id)
                .name(name)
                .slug(slug)
                .shortDescription(shortDescription)
                .primaryImage(primaryImage)
                .basePrice(basePrice.intValueExact())
                .salePrice(salePrice.intValue())
                .currency(currency)
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

        public static ProductImageCreateResponse of(Long id, String url, String altText,
            Boolean isPrimary, Integer displayOrder) {
            return ProductImageCreateResponse.builder()
                .id(id)
                .url(url)
                .altText(altText)
                .isPrimary(isPrimary)
                .displayOrder(displayOrder)
                .build();
        }

        public static ProductImageCreateResponse of(Long id, String url, String altText,
            Boolean isPrimary, Integer displayOrder, Long optionId) {
            return ProductImageCreateResponse.builder()
                .id(id)
                .url(url)
                .altText(altText)
                .isPrimary(isPrimary)
                .displayOrder(displayOrder)
                .optionId(optionId)
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

        public static ProductOptionGroupResponse of(Long id, String name, Integer displayOrder,
            List<ProductOptionResponse> options) {
            return ProductOptionGroupResponse.builder()
                .id(id)
                .name(name)
                .displayOrder(displayOrder)
                .options(options)
                .build();
        }
    }

    @Builder
    public record ProductPriceResponse(
        Integer basePrice,
        Integer salePrice,
        String currency,
        Double taxRate,
        Double discountPercentage
    ) {

        public static ProductPriceResponse of(Double basePrice, Double salePrice,
            String currency, Double taxRate, Double discountPercentage) {
            return ProductPriceResponse.builder()
                .basePrice(basePrice.intValue())
                .salePrice(salePrice.intValue())
                .currency(currency)
                .taxRate(taxRate)
                .discountPercentage(discountPercentage)
                .build();
        }
    }
}
