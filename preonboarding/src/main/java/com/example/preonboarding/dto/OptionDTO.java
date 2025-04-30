package com.example.preonboarding.dto;

import com.example.preonboarding.domain.ProductOption;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OptionDTO {
    private Long id;
    private String name;
    private double additionalPrice;
    private String sku;
    private int stock;
    private int displayOrder;

    public OptionDTO(ProductOption productOption) {
        this.id = productOption.getId();
        this.name = productOption.getName();
        this.additionalPrice = productOption.getAdditionalPrice();
        this.sku = productOption.getSku();
        this.stock = productOption.getStock();
        this.displayOrder = productOption.getDisplayOrder();
    }
}
