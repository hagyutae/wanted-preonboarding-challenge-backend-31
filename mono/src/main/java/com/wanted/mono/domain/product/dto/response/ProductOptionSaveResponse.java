package com.wanted.mono.domain.product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionSaveResponse {
    private Long id;
    @JsonProperty("option_group_id")
    private Long optionGroupId;
    private String name;
    @JsonProperty("additional_price")
    private BigDecimal additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;
}
