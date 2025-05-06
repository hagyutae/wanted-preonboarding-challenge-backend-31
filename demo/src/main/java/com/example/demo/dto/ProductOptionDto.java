package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionDto {
    private Long id;
    private Long optionGroupId;
    private String name;
    private Double additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;
}
