package com.example.demo.category.service;

import com.example.demo.category.CategoryError;
import com.example.demo.category.dto.CategoryPageResponse;
import com.example.demo.category.dto.CategoryQueryFilter;
import com.example.demo.category.dto.CategorySummary;
import com.example.demo.category.dto.CategoryTree;
import com.example.demo.category.entity.CategoryEntity;
import com.example.demo.category.repository.CategoryRepository;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.common.exception.GlobalException;
import com.example.demo.product.dto.ProductQueryFilter;
import com.example.demo.product.dto.ProductSummary;
import com.example.demo.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public List<CategoryTree> findAllByLevel(CategoryQueryFilter filter) {
        List<CategoryEntity> allCategories = categoryRepository.findAllByFilter(filter);

        Map<Long, CategoryTree> categoryTreeMap = allCategories.stream()
                .map(CategoryTree::of)
                .collect(Collectors.toMap(CategoryTree::id, Function.identity()));

        allCategories.forEach(category -> {
            if (category.getParent() != null) {
                CategoryTree parentTree = categoryTreeMap.get(category.getParent().getId());
                if (parentTree != null) {
                    parentTree.children().add(categoryTreeMap.get(category.getId()));
                }
            }
        });

        return allCategories.stream()
                .map(category -> categoryTreeMap.get(category.getId()))
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryPageResponse findPageByCategory(
            CategoryQueryFilter categoryQueryFilter,
            Boolean includeSubcategories,
            Pageable pageable
    ) {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAllByFilter(categoryQueryFilter);
        if(categoryEntityList.isEmpty())
            throw new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, CategoryError.CATEGORY_NOR_FOUND);
        CategoryEntity parentCategoryEntity = categoryEntityList.getFirst().getParent();

        Set<Long> categoryIdSet = new HashSet<>();
        categoryIdSet.add(parentCategoryEntity.getId());

        if(Boolean.TRUE.equals(includeSubcategories)) {
            Set<Long> childCategoryIdSet = categoryEntityList.stream().map(CategoryEntity::getId)
                    .collect(Collectors.toSet());
            categoryIdSet.addAll(childCategoryIdSet);
        }

        ProductQueryFilter productQueryFilter = ProductQueryFilter.builder()
                .categoryIds(categoryIdSet)
                .build();

        Page<ProductSummary> productSummaryPage = productService.findProductSummaryPage(productQueryFilter, pageable);

        return CategoryPageResponse.of(productSummaryPage, CategorySummary.of(parentCategoryEntity));
    }
}
