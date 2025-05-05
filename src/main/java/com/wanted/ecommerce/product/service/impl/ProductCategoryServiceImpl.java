package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.category.dto.response.CategoryResponse;
import com.wanted.ecommerce.category.dto.response.ParentCategoryResponse;
import com.wanted.ecommerce.category.service.CategoryService;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductCategory;
import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.ProductCategoryRequest;
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

    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryService categoryService;

    @Override
    public List<ProductCategory> saveProductCategories(Product product,
        List<ProductCategoryRequest> requests) {
        List<Long> categoryIds = requests.stream()
            .map(ProductCategoryRequest::getCategoryId)
            .toList();

        Map<Long, Category> categoryMap = categoryService.getCategoryByIds(categoryIds).stream()
            .collect(Collectors.toMap(Category::getId, Function.identity()));

        List<ProductCategory> productCategories = requests.stream()
            .map(request -> {
                Category category = categoryMap.get(request.getCategoryId());
                return ProductCategory.of(product, category, request.getIsPrimary());
            })
            .toList();
        return productCategoryRepository.saveAll(productCategories);
    }

    @Override
    public List<CategoryResponse> createCategoryResponse(List<ProductCategory> productCategories) {
        return productCategories.stream().map(productCategory ->
        {
            Category category = productCategory.getCategory();
            Category parent = category.getParent();
            if (parent == null) {
                return CategoryResponse.of(category.getId(), category.getName(), category.getSlug(),
                    productCategory.isPrimary());
            }
            ParentCategoryResponse parentResponse = ParentCategoryResponse.of(parent.getId(),
                parent.getName(), parent.getSlug());
            return CategoryResponse.of(category.getId(), category.getName(), category.getSlug(),
                productCategory.isPrimary(), parentResponse);
        }).toList();
    }

    @Override
    public List<ProductCategory> getCategoriesByCategoryIdAndProductId(Long categoryId,
        Long productId) {
        return productCategoryRepository.findByCategoryIdAndProductId(categoryId, productId);
    }

    @Override
    public void updateCategories(Product product, List<ProductCategoryRequest> requests) {
        requests.forEach(request -> {
            List<ProductCategory> categories = product.getCategories();
            Category category = categoryService.getCategoryById(
                request.getCategoryId());
            categories.forEach(productCategory -> productCategory.update(product, category,
                request.getIsPrimary()));
        });
    }
}