package com.mkhwang.wantedcqrs.product.domain.dto;

import com.mkhwang.wantedcqrs.common.dto.PageRequestDto;
import com.mkhwang.wantedcqrs.product.domain.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductSearchDto extends PageRequestDto {
  private ProductStatus status;
  private Integer minPrice;
  private Integer maxPrice;
  private List<Long> category;
  private Long seller;
  private Long brand;
  private Boolean inStock;
  private String search;
}
