package com.june.ecommerce.dto.product.create;

import com.june.ecommerce.dto.product.ProductDetailDto;
import com.june.ecommerce.dto.product.ProductPriceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private int sellerId;
    private int brandId;
    private String status;

    private ProductDetailCreateDto detail;
    private ProductPriceCreateDto price;
    private List<ProductCategoryCreateDto> categories;
    private List<ProductOptionGroupCreateDto> optionGroups;
    private List<ProductImageCreateDto> images;
    private List<Integer> tags;
}
