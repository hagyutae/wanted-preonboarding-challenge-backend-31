package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 정보 DTO")
public class FindParentCategoryDTO {
    @Schema(description = "카테고리 식별자")
    private Long id;

    @Schema(description = "카테고리 이름")
    private String name;

    @Schema(description = "슬러그")
    private String slug;

    public static FindParentCategoryDTO from(Category category) {
        FindParentCategoryDTO findParentCategoryDTO = new FindParentCategoryDTO();
        findParentCategoryDTO.id = category.getId();
        findParentCategoryDTO.name = category.getName();
        findParentCategoryDTO.slug = category.getSlug();
        return findParentCategoryDTO;
    }
}
