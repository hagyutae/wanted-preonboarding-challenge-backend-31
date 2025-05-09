package com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionCreateRequest {
    private String name;
    private Integer additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;
}
