package com.wanted.mono.domain.product.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.mono.domain.product.entity.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductOptionDto {
    private Long id;

    private String name;
    @JsonProperty("additional_price")
    private BigDecimal additionalPrice;
    private String sku;
    private Integer stock;
    @JsonProperty("display_order")
    private Integer displayOrder;

    // ----------------

    public static ProductOptionDto of(ProductOption productOption) {
        ProductOptionDto dto = new ProductOptionDto();
        dto.id = productOption.getId();
        dto.name = productOption.getName();
        dto.additionalPrice = productOption.getAdditionalPrice();
        dto.sku = productOption.getSku();
        dto.stock = productOption.getStock();
        dto.displayOrder = productOption.getDisplayOrder();
        return dto;
    }
}
