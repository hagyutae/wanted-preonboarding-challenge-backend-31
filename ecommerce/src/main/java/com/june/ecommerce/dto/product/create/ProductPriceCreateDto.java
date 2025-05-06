package com.june.ecommerce.dto.product.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceCreateDto {
    private int basePrice;
    private int salePrice;
    private int costPrice;
    private String currency;
    private int taxRate;
}
