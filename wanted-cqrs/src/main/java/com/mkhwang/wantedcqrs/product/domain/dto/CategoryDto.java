package com.mkhwang.wantedcqrs.product.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryDto {

  private Long id;
  private String name;
  private String slug;
  private String description;
  private Integer level;
  private String imageUrl;
  private List<CategoryDto> children;

  public void addChildren(CategoryDto categoryDto) {
    if (this.children == null) {
      this.children = new ArrayList<>();
    }
    this.children.add(categoryDto);
  }
}
