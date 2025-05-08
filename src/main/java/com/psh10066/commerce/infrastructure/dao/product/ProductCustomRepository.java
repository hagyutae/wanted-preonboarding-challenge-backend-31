package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.api.dto.request.GetAllProductsRequest;
import com.psh10066.commerce.api.dto.request.GetCategoryProductsRequest;
import com.psh10066.commerce.api.dto.response.GetAllProductsResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductCustomRepository {

    Page<GetAllProductsResponse> getAllProducts(GetAllProductsRequest request);

    Page<GetAllProductsResponse> getCategoryProducts(Long categoryId, GetCategoryProductsRequest request);

    List<GetAllProductsResponse> getNewProducts();

    List<GetAllProductsResponse> getPopularProducts();
}
