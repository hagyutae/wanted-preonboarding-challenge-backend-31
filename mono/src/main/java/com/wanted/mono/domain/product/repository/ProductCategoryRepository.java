package com.wanted.mono.domain.product.repository;

import com.wanted.mono.domain.category.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
