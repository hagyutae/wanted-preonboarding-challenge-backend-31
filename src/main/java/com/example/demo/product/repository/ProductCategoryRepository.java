package com.example.demo.product.repository;

import com.example.demo.product.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long>, ProductCategoryRepositoryCustom {
}
