package com.june.ecommerce.repository.category;

import com.june.ecommerce.domain.productcategory.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
