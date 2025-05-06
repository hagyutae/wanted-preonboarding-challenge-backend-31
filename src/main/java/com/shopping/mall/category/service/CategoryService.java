package com.shopping.mall.category.service;

import com.shopping.mall.category.dto.CategoryProductResponse;
import com.shopping.mall.category.dto.CategoryResponse;
import com.shopping.mall.category.entity.Category;
import com.shopping.mall.category.repository.CategoryRepository;
import com.shopping.mall.common.exception.ResourceNotFoundException;
import com.shopping.mall.product.entity.Product;
import com.shopping.mall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<CategoryResponse> getCategories() {
        List<Category> rootCategories = categoryRepository.findByParentIsNullOrderByLevelAsc();

        return rootCategories.stream()
                .map(CategoryResponse::from)
                .toList();
    }

    public List<CategoryProductResponse> getProductsByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("카테고리가 존재하지 않습니다."));

        List<Product> products = productRepository.findByCategory(category.getId());

        return products.stream()
                .map(CategoryProductResponse::from)
                .toList();
    }
}
