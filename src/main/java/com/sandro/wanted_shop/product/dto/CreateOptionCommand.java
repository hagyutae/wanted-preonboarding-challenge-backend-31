package com.sandro.wanted_shop.product.dto;

import com.sandro.wanted_shop.product.entity.ProductOption;
import com.sandro.wanted_shop.product.entity.relation.ProductOptionGroup;

import java.math.BigDecimal;

public record CreateOptionCommand(
        Long optionGroupId,
        String name,
        BigDecimal additionalPrice,
        String sku,
        Integer stock,
        Integer displayOrder
) {
    public CreateOptionCommand(String name, BigDecimal additionalPrice, String sku, Integer stock, Integer displayOrder) {
        this(null, name, additionalPrice, sku, stock, displayOrder);
    }

    public ProductOption toEntity(ProductOptionGroup productOptionGroup) {
        return ProductOption.builder()
                .optionGroup(productOptionGroup)
                .name(name)
                .additionalPrice(additionalPrice)
                .sku(sku)
                .stock(stock)
                .displayOrder(displayOrder)
                .build();
    }
}
