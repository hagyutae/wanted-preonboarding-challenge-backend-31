package com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsByCategoryCondition {

    private Integer page = 1;                        // 기본값 1
    private Integer perPage = 10;                    // 기본값 10
    private String sort = "created_at:desc";         // 정렬 기준 (예: created_at:desc)
    private boolean includeSubcategories = true;
}
