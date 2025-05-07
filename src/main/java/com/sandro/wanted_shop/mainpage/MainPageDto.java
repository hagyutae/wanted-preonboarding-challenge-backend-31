package com.sandro.wanted_shop.mainpage;

import com.sandro.wanted_shop.category.CategoryDto;
import com.sandro.wanted_shop.product.dto.ProductListDto;

import java.util.List;

public record MainPageDto(
        List<?> product,
        List<ProductListDto> newProduct,
        List<CategoryDto> categories
) {
    public static MainPageDto of(List<?> product, List<ProductListDto> newProduct, List<CategoryDto> categories) {
        return new MainPageDto(product, newProduct, categories);
    }
}
