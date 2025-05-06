package com.sandro.wanted_shop.product.dto;

import com.sandro.wanted_shop.product.entity.Product;
import com.sandro.wanted_shop.product.entity.ProductImage;
import com.sandro.wanted_shop.product.entity.ProductOption;

public record CreateImageCommand(
        String url,
        String altText,
        Boolean isPrimary,
        Integer displayOrder,
        Long optionId
) {
    public CreateImageCommand(String url,
                              String altText,
                              Boolean isPrimary,
                              Integer displayOrder) {
        this(url, altText, isPrimary, displayOrder, null);
    }

    public ProductImage toEntity(Product product) {
        return toEntity(product, null);
    }

    public ProductImage toEntity(Product product, ProductOption productOption) {
        return new ProductImage(
                product,
                url,
                altText,
                isPrimary,
                displayOrder,
                productOption
        );
    }
}
