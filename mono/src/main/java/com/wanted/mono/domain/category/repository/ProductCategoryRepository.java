package com.wanted.mono.domain.category.repository;

import com.wanted.mono.domain.category.entity.ProductCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    @EntityGraph(attributePaths = "category")
    Optional<ProductCategory> findById(Long id);
}
