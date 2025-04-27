package com.mkhwang.wantedcqrs.product.domain.dto;

import com.mkhwang.wantedcqrs.product.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

  private Long id;
  private String name;
  private String slug;
  private boolean primary;
  private Category parent;

}
