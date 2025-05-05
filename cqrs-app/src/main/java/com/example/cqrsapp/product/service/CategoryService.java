package com.example.cqrsapp.product.service;

import com.example.cqrsapp.common.dto.ProductSummaryItem;
import com.example.cqrsapp.common.exception.ResourceNotFoundException;
import com.example.cqrsapp.product.domain.Category;
import com.example.cqrsapp.product.dto.response.CategoryResponse;
import com.example.cqrsapp.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoriesByLevel(int level) {
        List<Category> categories = categoryRepository.findByLevelGreaterThanEqual(level);
        return CategoryResponse.fromEntity(categories);
    }

}
