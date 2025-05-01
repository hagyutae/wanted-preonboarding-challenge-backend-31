package com.wanted.mono.domain.product.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductDetailRequest {
    private BigDecimal weight;
    private Dimension dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private AdditionalInfo additionalInfo;

}