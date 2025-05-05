package com.june.ecommerce.dto.option;

import com.june.ecommerce.domain.productoption.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionDto {
    private int id;
    private String name;
    private int additionalPrice;
    private String sku;
    private int stock;
    private int displayOrder;

    public static ProductOptionDto fromEntity(ProductOption productOption) {
        return ProductOptionDto.builder()
                .id(productOption.getId())
                .name(productOption.getName())
                .additionalPrice(productOption.getAdditionalPrice())
                .sku(productOption.getSku())
                .stock(productOption.getStock())
                .displayOrder(productOption.getDisplayOrder())
                .build();
    }
}
