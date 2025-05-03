package com.preonboarding.service.category;

import com.preonboarding.domain.Category;
import com.preonboarding.domain.ProductCategory;
import com.preonboarding.dto.request.CategoryRequestDto;

import java.util.List;

public interface CategoryService {
    List<ProductCategory> createProductCategories(List<CategoryRequestDto> dtoList);
}
