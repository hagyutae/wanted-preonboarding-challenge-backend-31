package com.wanted.mono.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductOptionRequest {
    private String name;
    @JsonProperty("additional_price")
    private BigDecimal additionalPrice;
    private String sku;
    private Integer stock;
    @JsonProperty("display_order")
    private Integer displayOrder;
}
