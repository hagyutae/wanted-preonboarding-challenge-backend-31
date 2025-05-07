package com.challenge.onboarding.category.repository;

import com.challenge.onboarding.category.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
