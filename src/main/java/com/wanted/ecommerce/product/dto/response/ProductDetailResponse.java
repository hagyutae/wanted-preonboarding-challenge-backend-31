package com.wanted.ecommerce.product.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.ecommerce.brand.dto.response.BrandDetailResponse;
import com.wanted.ecommerce.category.dto.response.CategoryResponse;
import com.wanted.ecommerce.review.dto.response.RatingResponse;
import com.wanted.ecommerce.seller.dto.response.SellerDetailResponse;
import com.wanted.ecommerce.tag.dto.response.TagResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDetailResponse(
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
    List<ProductDetailImageResponse> images,
    List<TagResponse> tags,
    RatingResponse rating,
    List<RelatedProductResponse> relatedProducts

) {

    public static ProductDetailResponse of(Long id, String name, String slug,
        String shortDescription, String fullDescription, SellerDetailResponse seller,
        BrandDetailResponse brand, String status, LocalDateTime createdAt, LocalDateTime updatedAt,
        DetailResponse detail, ProductPriceResponse price, List<CategoryResponse> categories,
        List<ProductOptionGroupResponse> optionGroups, List<ProductDetailImageResponse> images,
        List<TagResponse> tags, RatingResponse rating, List<RelatedProductResponse> relatedProducts) {
        return ProductDetailResponse.builder()
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
}
