package com.shopping.mall.category.dto;

import com.shopping.mall.product.entity.Product;
import com.shopping.mall.product.entity.ProductImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryProductResponse {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private Integer basePrice;
    private Integer salePrice;
    private String currency;
    private String imageUrl;

    public static CategoryProductResponse from(Product product) {
        return CategoryProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .basePrice(product.getProductPrice().getBasePrice())
                .salePrice(product.getProductPrice().getSalePrice())
                .currency(product.getProductPrice().getCurrency())
                .imageUrl(product.getProductImages().stream()
                        .filter(ProductImage::getIsPrimary)
                        .findFirst()
                        .map(ProductImage::getUrl)
                        .orElse(null))
                .build();
    }
}