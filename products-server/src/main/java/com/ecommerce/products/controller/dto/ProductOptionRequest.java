package com.ecommerce.products.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductOptionRequest(
    String name,
    BigDecimal additionalPrice,
    String sku,
    Integer stock,
    Integer displayOrder
) {

}