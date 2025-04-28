package com.mkhwang.wantedcqrs.product.domain.dto.category;

import com.mkhwang.wantedcqrs.config.advice.dto.ApiPageInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategorySearchResultDto {

  private CategoryDto category;
  private List<CategoryProduct> items;
  private ApiPageInfo pagination;
}
