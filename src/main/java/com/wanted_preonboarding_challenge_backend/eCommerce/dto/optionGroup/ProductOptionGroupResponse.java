package com.wanted_preonboarding_challenge_backend.eCommerce.dto.optionGroup;

import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.response.ProductOptionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductOptionGroupResponse {
    private Long id;
    private String name;
    private Integer displayOrder;
    private List<ProductOptionResponse> options;
}
