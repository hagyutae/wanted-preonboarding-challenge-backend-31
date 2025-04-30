package com.mkhwang.wantedcqrs.product.domain.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mkhwang.wantedcqrs.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties({"categoryId"})
public class CategorySearchDto extends PageRequestDto {
  private Long categoryId;
  private Boolean includeSubcategories;
}
