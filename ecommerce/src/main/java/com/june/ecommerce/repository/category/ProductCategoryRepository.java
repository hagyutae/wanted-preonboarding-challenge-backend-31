package com.june.ecommerce.repository.category;

import com.june.ecommerce.domain.productcategory.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    Collection<ProductCategory> findByProductId(int productId);
}
