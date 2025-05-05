package com.wanted.ecommerce.category.service;

import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.category.dto.response.CategoriesResponse;
import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long id);

    List<CategoriesResponse> getAllCategoryByLevel(int level);
}
