package com.mkhwang.wantedcqrs.product.domain.dto.category;

import com.mkhwang.wantedcqrs.common.dto.PageRequestDto;
import lombok.Getter;

@Getter
public class CategorySearchDto extends PageRequestDto {
  private Boolean includeSubcategories;
}
