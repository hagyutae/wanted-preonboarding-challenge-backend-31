package com.wanted.ecommerce.category.service;

import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.category.repository.CategoryRepository;
import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->
        new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
    }
}
