package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
