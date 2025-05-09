package com.preonboarding.challenge.controller.dto;

import com.preonboarding.challenge.service.dto.PaginationDto;
import com.preonboarding.challenge.service.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListResponse {
    private List<ProductDto.ProductSummary> items;
    private PaginationDto.PaginationInfo pagination;
}