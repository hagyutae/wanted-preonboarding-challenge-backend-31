package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.category.dto.response.CategoryResponse;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductCategory;
import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.ProductCategoryRequest;
import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> saveProductCategories(Product product, List<ProductCategoryRequest> categoryRequestList);
    List<CategoryResponse> createCategoryResponse(List<ProductCategory> productCategories);
    void updateCategories(Product product, List<ProductCategoryRequest> requests);
}
