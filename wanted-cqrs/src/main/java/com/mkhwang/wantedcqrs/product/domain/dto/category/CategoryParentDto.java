package com.mkhwang.wantedcqrs.product.domain.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryParentDto {

  private Long id;
  private String name;
  private String slug;

  public static CategoryParentDto of(Long id, String name, String slug) {
    CategoryParentDto dto = new CategoryParentDto();
    dto.setId(id);
    dto.setName(name);
    dto.setSlug(slug);
    return dto;
  }
}
