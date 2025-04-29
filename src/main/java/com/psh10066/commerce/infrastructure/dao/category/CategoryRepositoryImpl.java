package com.psh10066.commerce.infrastructure.dao.category;

import com.psh10066.commerce.domain.exception.ResourceNotFoundException;
import com.psh10066.commerce.domain.model.category.Category;
import com.psh10066.commerce.domain.model.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category getById(Long id) {
        return categoryJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }
}
