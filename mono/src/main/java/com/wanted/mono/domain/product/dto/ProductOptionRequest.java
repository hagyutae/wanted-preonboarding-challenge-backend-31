package com.wanted.mono.domain.product.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductOptionRequest {
    private String name;
    private BigDecimal additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;
}
