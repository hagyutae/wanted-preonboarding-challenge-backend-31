package com.preonboarding.service.product;

import com.preonboarding.dto.request.ProductCreateRequestDto;
import com.preonboarding.dto.response.ProductResponse;
import com.preonboarding.global.response.BaseResponse;

public interface ProductService {
    BaseResponse<ProductResponse> createProduct(ProductCreateRequestDto dto);
}
