package com.example.demo.category.repository;

import com.example.demo.category.dto.CategoryQueryFilter;
import com.example.demo.category.entity.CategoryEntity;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<CategoryEntity> findAllByFilter(CategoryQueryFilter filter);
}
