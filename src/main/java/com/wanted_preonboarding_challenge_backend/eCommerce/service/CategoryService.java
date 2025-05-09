package com.wanted_preonboarding_challenge_backend.eCommerce.service;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Category;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.category.response.CategoryTreeResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.mapper.CategoryMapper;
import com.wanted_preonboarding_challenge_backend.eCommerce.respository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public List<CategoryTreeResponse> getCategoryTree(int level) {

        List<Category> topLevelCategories = categoryRepository.findByLevel(level);

        List<CategoryTreeResponse> result = topLevelCategories.stream()
                .map(this::buildCategoryTree)
                .collect(Collectors.toList());

        return result;
    }

    private CategoryTreeResponse buildCategoryTree(Category category) {
        CategoryTreeResponse dto = categoryMapper.toDto(category);

        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            List<CategoryTreeResponse> childDtos = category.getChildren().stream()
                    .map(this::buildCategoryTree)
                    .collect(Collectors.toList());
            dto.setChildren(childDtos);
        } else {
            dto.setChildren(List.of());
        }

        return dto;
    }

}
