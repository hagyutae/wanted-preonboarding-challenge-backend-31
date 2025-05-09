package com.wanted_preonboarding_challenge_backend.eCommerce.dto.category.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentCategoryResponse {
    private Long id;
    private String name;
    private String slug;
}
