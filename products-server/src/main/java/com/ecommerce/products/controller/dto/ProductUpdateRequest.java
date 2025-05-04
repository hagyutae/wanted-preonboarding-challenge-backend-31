package com.ecommerce.products.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductUpdateRequest (
    String name,
    String slug,
    String shortDescription,
    String fullDescription,
    Long sellerId,
    Long brandId,
    String status,

    ProductCreateRequest.ProductDetailDto detail,
    ProductCreateRequest.ProductPriceDto price,
    List<ProductCreateRequest.ProductCategoryDto> categories,
    List<Long> tagIds
) { }