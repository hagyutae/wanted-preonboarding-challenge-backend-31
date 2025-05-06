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
public class ProductPriceDto {
    private Long id;
    private Long productId;
    private Double basePrice;
    private Double costPrice;
    private String currency;
    private Double taxRate;
}
