package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.category.dto.response.CategoryResponse;
import com.wanted.ecommerce.category.dto.response.ParentCategoryResponse;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductCategory;
import com.wanted.ecommerce.product.dto.request.ProductCategoryRequest;
import com.wanted.ecommerce.product.repository.ProductCategoryRepository;
import com.wanted.ecommerce.product.service.ProductCategoryService;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository categoryRepository;

    @Override
    public List<ProductCategory> saveProductCategories(Product product,
        List<ProductCategoryRequest> categoryRequestList) {
        List<Long> categoryIds = categoryRequestList.stream()
            .map(ProductCategoryRequest::getCategoryId)
            .toList();
        Map<Long, Category> categoryMap = categoryRepository.findAllById(categoryIds).stream()
            .map(ProductCategory::getCategory)
            .collect(Collectors.toMap(Category::getId, Function.identity()));

        List<ProductCategory> productCategories = categoryRequestList.stream()
            .map(categoryRequest -> {
                Long categoryId = categoryRequest.getCategoryId();
                Category category = categoryMap.get(categoryId);
                return ProductCategory.of(product, category, categoryRequest.getIsPrimary());
            })
            .toList();

        return  categoryRepository.saveAll(productCategories);
    }

    @Override
    public List<CategoryResponse> createCategoryResponse(List<ProductCategory> productCategories) {
        return productCategories.stream().map(productCategory ->
        {
            Category category = productCategory.getCategory();
            Category parent = category.getParent();
            ParentCategoryResponse parentResponse = ParentCategoryResponse.of(parent.getId(),
                parent.getName(), parent.getSlug());
            return CategoryResponse.of(category.getId(), category.getName(), category.getSlug(),
                productCategory.isPrimary(), parentResponse);
        }).toList();
    }
}
