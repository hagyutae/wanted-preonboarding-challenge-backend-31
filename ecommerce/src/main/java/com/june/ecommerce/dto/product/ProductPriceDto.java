package com.june.ecommerce.dto.product;

import com.june.ecommerce.domain.productprice.ProductPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPriceDto {
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private String currency;
    private BigDecimal taxRate;

    public static ProductPriceDto fromEntity(ProductPrice entity) {
        if (entity == null) return null;
        return ProductPriceDto.builder()
                .basePrice(entity.getBasePrice())
                .salePrice(entity.getSalePrice())
                .costPrice(entity.getCostPrice())
                .currency(entity.getCurrency())
                .taxRate(entity.getTaxRate())
                .build();
    }
}
