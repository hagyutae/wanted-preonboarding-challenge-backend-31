package com.wanted.mono.domain.category.entity.dto.response;

import com.wanted.mono.domain.category.entity.dto.CategoryDto;
import com.wanted.mono.domain.category.entity.dto.ProductCategoryDto;
import com.wanted.mono.domain.product.dto.ProductSearchItem;
import com.wanted.mono.global.common.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductsResponse {
    private CategoryDto category;
    private List<ProductSearchItem> items;
    private Pagination pagination;
}
