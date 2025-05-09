package com.wanted_preonboarding_challenge_backend.eCommerce.dto.category.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductCategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private boolean isPrimary;
    private ParentCategoryResponse parent;
}
