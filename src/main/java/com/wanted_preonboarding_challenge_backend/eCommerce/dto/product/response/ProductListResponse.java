package com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response;

import com.wanted_preonboarding_challenge_backend.eCommerce.common.PaginationDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.ProductSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {
    private List<ProductSummaryDto> items;
    private PaginationDto pagination;
}
