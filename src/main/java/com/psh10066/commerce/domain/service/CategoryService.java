package com.psh10066.commerce.domain.service;

import com.psh10066.commerce.api.dto.request.GetCategoryProductsRequest;
import com.psh10066.commerce.api.dto.response.GetAllCategoryResponse;
import com.psh10066.commerce.api.dto.response.GetAllProductsResponse;
import com.psh10066.commerce.api.dto.response.PaginationWithCategoryResponse;
import com.psh10066.commerce.domain.model.category.Category;
import com.psh10066.commerce.domain.model.category.CategoryRepository;
import com.psh10066.commerce.domain.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<GetAllCategoryResponse> getAllCategory(Integer level) {
        return categoryRepository.findAllByLevel(level).stream()
            .map(this::getCategoryResponse)
            .toList();
    }

    private GetAllCategoryResponse getCategoryResponse(Category category) {
        List<GetAllCategoryResponse> children = null;
        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            children = category.getChildren().stream().map(this::getCategoryResponse).toList();
        }
        return new GetAllCategoryResponse(
            category.getId(),
            category.getName(),
            category.getSlug(),
            category.getDescription(),
            category.getLevel(),
            category.getImageUrl(),
            children
        );
    }

    public PaginationWithCategoryResponse<GetAllProductsResponse> getCategoryProduct(Long id, GetCategoryProductsRequest request) {
        Category category = categoryRepository.getById(id);
        Page<GetAllProductsResponse> products = productRepository.getCategoryProducts(id, request);
        return PaginationWithCategoryResponse.of(category, products, product -> product);
    }
}
