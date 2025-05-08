package com.psh10066.commerce.api.dto.response;

import com.psh10066.commerce.domain.model.product.ProductStatus;
import com.psh10066.commerce.domain.model.review.ReviewFirstCollection;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
public record GetProductDetailResponse(
    Long id,
    String name,
    String slug,
    String shortDescription,
    String fullDescription,

    Seller seller,
    Brand brand,

    ProductStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,

    Detail detail,
    Price price,
    List<Category> categories,

    List<OptionGroup> optionGroups,
    List<Image> images,
    List<Tag> tags,
    Rating rating,

    List<GetAllProductsResponse> relatedProducts
) {

    public record Seller(
        Long id,
        String name,
        String description,
        String logoUrl,
        BigDecimal rating,
        String contactEmail,
        String contactPhone
    ) {
    }

    public record Brand(
        Long id,
        String name,
        String description,
        String logoUrl,
        String website
    ) {
    }

    public record PrimaryImage(
        String url,
        String altText
    ) {
    }

    public record Detail(
        BigDecimal weight,
        Dimensions dimensions,
        String materials,
        String countryOfOrigin,
        String warrantyInfo,
        String careInstructions,
        Map<String, Object> additionalInfo
    ) {
    }

    public record Dimensions(
        Integer width,
        Integer height,
        Integer depth
    ) {
    }

    public record Price(
        BigDecimal basePrice,
        BigDecimal salePrice,
        String currency,
        BigDecimal taxRate,
        Integer discountPercentage
    ) {
    }

    public record Category(
        Long id,
        String name,
        String slug,
        Boolean isPrimary,
        ParentCategory parent
    ) {
    }

    public record ParentCategory(
        Long id,
        String name,
        String slug
    ) {
    }

    public record OptionGroup(
        Long id,
        String name,
        Integer displayOrder,
        List<Option> options
    ) {
    }

    public record Option(
        Long id,
        String name,
        BigDecimal additionalPrice,
        String sku,
        Integer stock,
        Integer displayOrder
    ) {
    }

    public record Image(
        Long id,
        String url,
        String altText,
        Boolean isPrimary,
        Integer displayOrder,
        Long optionId
    ) {
    }

    public record Tag(
        Long id,
        String name,
        String slug
    ) {
    }

    public record Rating(
        BigDecimal average,
        Integer count,
        ReviewFirstCollection.Distribution distribution
    ) {
    }
}
