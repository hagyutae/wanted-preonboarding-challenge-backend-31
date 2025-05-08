package com.psh10066.commerce.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record GetAllCategoryResponse(
    Long id,
    String name,
    String slug,
    String description,
    Integer level,
    String imageUrl,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<GetAllCategoryResponse> children
) {
}
