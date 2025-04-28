package com.mkhwang.wantedcqrs.product.domain.dto.category;

import com.mkhwang.wantedcqrs.config.advice.dto.ApiPageInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategorySearchResultDto {

  private CategorySearchCategoryDto category;
  private List<CategoryProduct> items;
  private ApiPageInfo pagination;

  public static CategorySearchResultDto of(
      CategorySearchCategoryDto category,
      List<CategoryProduct> items,
      ApiPageInfo pagination) {
    CategorySearchResultDto dto = new CategorySearchResultDto();
    dto.setCategory(category);
    dto.setItems(items);
    dto.setPagination(pagination);
    return dto;
  }
}
