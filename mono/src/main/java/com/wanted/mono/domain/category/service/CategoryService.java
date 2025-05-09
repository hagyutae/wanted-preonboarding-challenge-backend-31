package com.wanted.mono.domain.category.service;

import com.wanted.mono.domain.category.entity.Category;
import com.wanted.mono.domain.category.entity.dto.CategoryDto;
import com.wanted.mono.domain.category.entity.dto.ProductCategoryDto;
import com.wanted.mono.domain.category.repository.CategoryRepository;
import com.wanted.mono.global.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findByIds(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    public List<Category> findAllByIdsWithProductCategory(List<Long> categoryIds) {
        return categoryRepository.findAllByIdIn(categoryIds);
    }

    public List<CategoryDto> findAllByLevel(Integer level) {
        // 카테고리 수가 많지 않기 때문에 메모리에 올려도 성능 문제는 없음
        List<Category> allCategories = categoryRepository.findAll();

        // 데이터를 DTO로 변환 및 Map에 저장
        Map<Long, CategoryDto> map = new HashMap<>();
        List<CategoryDto> roots = new ArrayList<>();
        for (Category entity : allCategories) {
            CategoryDto dto = CategoryDto.of(entity);
            map.put(dto.getId(), dto);
        }

        // 모든 카테고리를 탐색
        // 부모를 가지고 있다면 부모의 자식 리스트에 나를 추가
        for (Category entity : allCategories) {
            Long parentId = entity.getParent() != null ? entity.getParent().getId() : null;
            if (parentId == null) {
                roots.add(map.get(entity.getId()));
            } else {
                CategoryDto parent = map.get(parentId);
                if (parent.getChildren() == null) parent.setChildren(new ArrayList<>());
                parent.getChildren().add(map.get(entity.getId()));
            }
        }

        // level이 null이면 전체 트리 반환
        if (level == null) return roots;

        // level 파라미터가 주어졌다면 필터링
        List<CategoryDto> filtered = new ArrayList<>();
        collectByLevel(roots, level, filtered);
        return filtered;
    }

    private void collectByLevel(List<CategoryDto> nodes, int level, List<CategoryDto> result) {
        for (CategoryDto node : nodes) {
            if (node.getLevel() != null && node.getLevel() == level) {
                result.add(node);
            }
            if (node.getChildren() != null) {
                collectByLevel(node.getChildren(), level, result);
            }
        }
    }

    public CategoryDto findById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        return CategoryDto.of(category);
    }

}
