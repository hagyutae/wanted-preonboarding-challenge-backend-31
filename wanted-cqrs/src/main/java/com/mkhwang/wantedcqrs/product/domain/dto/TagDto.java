package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagDto {

  private Long id;
  private String name;
  private String slug;
}
