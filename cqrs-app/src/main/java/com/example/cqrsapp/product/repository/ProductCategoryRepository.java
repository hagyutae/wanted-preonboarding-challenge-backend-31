package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.product.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
