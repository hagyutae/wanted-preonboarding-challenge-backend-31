package com.preonboarding.dto.request.product;

import com.preonboarding.dto.request.category.CategoryRequestDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEditRequestDto {
    private String name;

    private String slug;

    private String shortDescription;

    private String fullDescription;

    private Long sellerId;

    private Long brandId;

    private String status;

    private ProductDetailRequestDto detail;

    private ProductPriceRequestDto price;

    private List<CategoryRequestDto> categories;

    private List<ProductOptionGroupRequestDto> optionGroups;

    private List<ProductImageRequestDto> images;

    private List<Long> tags;
}
