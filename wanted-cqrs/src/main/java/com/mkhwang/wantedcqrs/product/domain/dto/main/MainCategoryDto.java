package com.mkhwang.wantedcqrs.product.domain.dto.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainCategoryDto {

  private Long id;
  private String name;
  private String slug;
  private String imageUrl;
  @JsonIgnore
  private Integer level;
  @JsonProperty("product_count")
  private Long productCount;
  @JsonIgnore
  private Long parent;

  public MainCategoryDto(Long id, String name, String slug, Integer level, String imageUrl, Long parent, Long productCount) {
    this.id = id;
    this.name = name;
    this.slug = slug;
    this.level = level;
    this.imageUrl = imageUrl;
    this.productCount = productCount;
    this.parent = parent;
  }

  public void addCount(Long productCount) {
    this.productCount += productCount;
  }
}
