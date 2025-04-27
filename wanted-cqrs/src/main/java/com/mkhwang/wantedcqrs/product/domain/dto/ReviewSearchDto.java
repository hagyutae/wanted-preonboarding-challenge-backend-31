package com.mkhwang.wantedcqrs.product.domain.dto;

import com.mkhwang.wantedcqrs.common.dto.PageRequestDto;
import lombok.Getter;

@Getter
public class ReviewSearchDto extends PageRequestDto {
  private Integer rating;
}
