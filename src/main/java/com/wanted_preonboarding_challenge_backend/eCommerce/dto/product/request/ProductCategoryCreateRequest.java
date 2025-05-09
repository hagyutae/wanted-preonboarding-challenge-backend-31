package com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryCreateRequest {
    private Long categoryId;
    private Boolean isPrimary;
}
