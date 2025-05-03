package com.preonboarding.service.category;

import com.preonboarding.domain.Category;
import com.preonboarding.domain.ProductCategory;
import com.preonboarding.dto.request.CategoryRequestDto;
import com.preonboarding.global.code.ErrorCode;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.global.response.ErrorResponseDto;
import com.preonboarding.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductCategory> createProductCategories(List<CategoryRequestDto> dtoList) {
        List<ProductCategory> categories = new ArrayList<>();

        for (CategoryRequestDto requestDto : dtoList) {
            Category category = categoryRepository.findById(requestDto.getCategoryId())
                    .orElseThrow(() -> new BaseException(false, ErrorResponseDto.of(ErrorCode.CATEGORY_NOT_FOUND)));

            ProductCategory productCategory = ProductCategory.of(category,requestDto.isPrimary());
            categories.add(productCategory);
        }

        return categories;
    }
}
