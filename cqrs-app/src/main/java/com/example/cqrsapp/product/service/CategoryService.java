package com.example.cqrsapp.product.service;

import com.example.cqrsapp.common.dto.ProductSummaryItem;
import com.example.cqrsapp.common.exception.ResourceNotFoundException;
import com.example.cqrsapp.product.domain.Category;
import com.example.cqrsapp.product.dto.response.CategoryProductListResponse;
import com.example.cqrsapp.product.dto.response.CategoryResponse;
import com.example.cqrsapp.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoriesByLevel(int level) {
        List<Category> categories = categoryRepository.findByLevelGreaterThanEqual(level);
        return CategoryResponse.fromEntity(categories);
    }

    @Transactional(readOnly = true)
    public CategoryProductListResponse getCategoriesProduct(Long categoryId, Boolean includeSubcategories, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException( "Category" ,String.valueOf(categoryId)));

        List<Long> searchCategoryIds = getSearchCategoryId(categoryId, includeSubcategories);
        Page<ProductSummaryItem> page = categoryRepository.findAllByCategoryIdIn(searchCategoryIds, pageable);
        return new CategoryProductListResponse(category, page);
    }

    private List<Long> getSearchCategoryId(Long categoryId, Boolean includeSubcategories) {
        if(includeSubcategories) {
            List<Category> allDescendants = categoryRepository.findAllDescendants(categoryId);
            return allDescendants.stream().map(Category::getId).toList();
        } else {
            return List.of(categoryId);
        }
    }
}
