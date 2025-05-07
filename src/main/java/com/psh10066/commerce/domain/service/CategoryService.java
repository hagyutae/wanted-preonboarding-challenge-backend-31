package com.psh10066.commerce.domain.service;

import com.psh10066.commerce.api.dto.response.GetAllCategoryResponse;
import com.psh10066.commerce.domain.model.category.Category;
import com.psh10066.commerce.domain.model.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

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
}
