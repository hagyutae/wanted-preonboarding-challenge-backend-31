package com.preonboarding.service.category;

import com.preonboarding.domain.ProductCategory;
import com.preonboarding.dto.request.category.CategoryRequestDto;
import com.preonboarding.dto.response.category.CategoryResponse;
import com.preonboarding.dto.response.product.ProductPageResponse;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.global.response.paging.CategoryPageBaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CategoryService {
    BaseResponse<List<CategoryResponse>> getCategories(int level);
    CategoryPageBaseResponse<ProductPageResponse> getProductsByCategory(Long id,Integer page,Integer perPage,String sort);

    List<ProductCategory> createProductCategories(List<CategoryRequestDto> dtoList);
}
