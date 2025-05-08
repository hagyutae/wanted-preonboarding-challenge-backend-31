package com.pawn.wantedcqrs.productOptionGroup.repository;

import com.pawn.wantedcqrs.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
