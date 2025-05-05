package com.wanted.ecommerce.category.service;

import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.category.dto.response.CategoriesResponse;
import com.wanted.ecommerce.category.repository.CategoryRepository;
import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoriesResponse> getAllCategoryByLevel(int level) {
        List<Category> parentCategories = categoryRepository.findAllByLevel(level);

        return parentCategories.stream()
            .map(this::getChildrenCategories)
            .collect(Collectors.toList());
    }

    @Override
    public List<Category> getCategoryByIds(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    private CategoriesResponse getChildrenCategories(Category category) {
        List<Category> childCategories = categoryRepository.findAllByParentId(category.getId());

        List<CategoriesResponse> childResponses = childCategories.stream()
            .map(this::getChildrenCategories)
            .collect(Collectors.toList());

        return CategoriesResponse.of(
            category.getId(),
            category.getName(),
            category.getSlug(),
            category.getDescription(),
            category.getLevel(),
            category.getImageUrl(),
            childResponses.isEmpty() ? null : childResponses
        );
    }
}
