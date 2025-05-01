package com.wanted.mono.domain.category.service;

import com.wanted.mono.domain.category.entity.Category;
import com.wanted.mono.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public List<Category> findByIds(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }
}
