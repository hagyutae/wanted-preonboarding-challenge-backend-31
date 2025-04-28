package com.mkhwang.wantedcqrs.product.domain.dto.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchResultDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainResponseDto {
  @JsonProperty("new_products")
  private List<ProductSearchResultDto> newProducts;
  @JsonProperty("popular_products")
  private List<ProductSearchResultDto> popularProducts;
  @JsonProperty("featured_categories")
  private List<MainCategoryDto> featuredCategories;

  public MainResponseDto(List<ProductSearchResultDto> newProducts, List<ProductSearchResultDto> popularProducts, List<MainCategoryDto> featuredCategories) {
    this.newProducts = newProducts;
    this.popularProducts = popularProducts;
    this.featuredCategories = featuredCategories;
  }

  public static MainResponseDto of(List<ProductSearchResultDto> newProducts, List<ProductSearchResultDto> popularProducts, List<MainCategoryDto> featuredCategories) {
    return new MainResponseDto(newProducts, popularProducts, featuredCategories);
  }
}
