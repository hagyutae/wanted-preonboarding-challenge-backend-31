package com.wanted.ecommerce.category.service;

import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.category.dto.request.PageableRequest;
import com.wanted.ecommerce.category.dto.response.CategoriesResponse;
import com.wanted.ecommerce.category.dto.response.CategoryProductListResponse;
import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long id);

    List<CategoriesResponse> getAllCategoryByLevel(int level);

    List<Category> getCategoryByIds(List<Long> ids);

    CategoryProductListResponse getCategoryProducts(Long categoryId, Boolean includeSubcategories, PageableRequest pageableRequest);
}
