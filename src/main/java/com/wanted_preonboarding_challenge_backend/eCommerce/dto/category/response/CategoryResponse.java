package com.wanted_preonboarding_challenge_backend.eCommerce.dto.category.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer level;
    @JsonProperty("image_url")
    private String imageUrl;
    private ParentCategoryResponse parent;
}
