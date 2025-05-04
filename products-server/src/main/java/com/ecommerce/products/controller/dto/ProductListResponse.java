package com.ecommerce.products.controller.dto;

import com.ecommerce.products.application.dto.PaginationDto;
import com.ecommerce.products.application.dto.ProductDto;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductListResponse(
    List<ProductDto.ProductSummary> items,
    PaginationDto.PaginationInfo pagination
) {}