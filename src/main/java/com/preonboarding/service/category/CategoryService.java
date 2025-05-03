package com.preonboarding.service.category;

import com.preonboarding.domain.ProductCategory;
import com.preonboarding.dto.request.category.CategoryRequestDto;
import com.preonboarding.dto.response.category.CategoryResponse;
import com.preonboarding.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    BaseResponse<List<CategoryResponse>> getCategories(int level);

    List<ProductCategory> createProductCategories(List<CategoryRequestDto> dtoList);
}
