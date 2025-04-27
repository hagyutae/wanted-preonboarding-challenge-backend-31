package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {

  private Long id;
  private String name;
  private String slug;
}
