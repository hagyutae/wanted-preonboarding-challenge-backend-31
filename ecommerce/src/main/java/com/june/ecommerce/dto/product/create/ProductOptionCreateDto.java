package com.june.ecommerce.dto.product.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionCreateDto {
    private String name;
    private int additionalPrice;
    private String sku;
    private int stock;
    private int displayOrder;
}
