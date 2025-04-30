package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.api.dto.request.GetAllProductsRequest;
import com.psh10066.commerce.api.dto.response.GetAllProductsResponse;
import org.springframework.data.domain.Page;

public interface ProductCustomRepository {

    Page<GetAllProductsResponse> getAllProducts(GetAllProductsRequest request);
}
