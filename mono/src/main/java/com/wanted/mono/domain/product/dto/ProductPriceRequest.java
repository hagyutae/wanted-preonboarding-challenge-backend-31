package com.wanted.mono.domain.product.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductPriceRequest {
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private String currency;
    private BigDecimal taxRate;
}

