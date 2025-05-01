package com.june.ecommerce.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionDto {
    private int id;
    private String name;
    private int stock;
    private String sku;
    private int displayOrder;
    private java.math.BigDecimal additionalPrice;
}
