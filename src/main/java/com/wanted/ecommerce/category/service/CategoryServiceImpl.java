package com.wanted.ecommerce.category.service;

import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.category.dto.request.PageableRequest;
import com.wanted.ecommerce.category.dto.response.CategoriesResponse;
import com.wanted.ecommerce.category.dto.response.CategoryProductListResponse;
import com.wanted.ecommerce.category.dto.response.CategoryResponse;
import com.wanted.ecommerce.category.repository.CategoryRepository;
import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductCategory;
import com.wanted.ecommerce.product.repository.ProductCategoryRepository;
import com.wanted.ecommerce.product.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;

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

    @Transactional(readOnly = true)
    @Override
    public List<Category> getCategoryByIds(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryProductListResponse getCategoryProducts(Long categoryId,
        Boolean includeSubcategories, PageableRequest pageableRequest) {
        int pageNumber = Math.max(0, pageableRequest.getPage() - 1);
        Pageable pageable = PageRequest.of(pageNumber, pageableRequest.getPerPage());

        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
        List<Long> categoryIds = getCategoryIncludeDescendants(categoryId, includeSubcategories);

//        Page<ProductItemResponse> productItemResponsePage = categoryRepository.findAllByCategoryIds(categoryIds, pageable);

        Page<Product> result = productRepository.findByCategoriesIdIn(categoryIds, pageable);
        ProductCategory productCategory = getProductCategory(categoryId);
        CategoryResponse categoryResponse = CategoryResponse.of(category, productCategory);

        return CategoryProductListResponse.of(categoryResponse, result);
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

    private List<Long> getCategoryIncludeDescendants(Long categoryId,
        Boolean includeSubcategories) {
        if (includeSubcategories) {
            return categoryRepository.findAllIncludeDescendants(categoryId).stream()
                .map(Category::getId)
                .toList();
        } else {
            return List.of(categoryId);
        }
    }

    @Transactional(readOnly = true)
    private ProductCategory getProductCategory(Long categoryId){
        return productCategoryRepository.findByCategoryId(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException(
                ErrorType.RESOURCE_NOT_FOUND));
    }
}
