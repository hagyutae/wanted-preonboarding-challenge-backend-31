package com.mkhwang.wantedcqrs.product.domain.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategorySearchCategoryDto {
  private Long id;
  private String name;
  private String slug;
  private String description;
  private Integer level;
  private String imageUrl;
  private CategoryParentDto parent;

  public static CategorySearchCategoryDto of(
      Long id, String name, String slug, String description, Integer level, String imageUrl, CategoryParentDto parent) {
    CategorySearchCategoryDto dto = new CategorySearchCategoryDto();
    dto.setId(id);
    dto.setName(name);
    dto.setSlug(slug);
    dto.setDescription(description);
    dto.setLevel(level);
    dto.setImageUrl(imageUrl);
    dto.setParent(parent);
    return dto;
  }
}
