package com.preonboarding.repository.product;

import com.preonboarding.domain.Product;
import com.preonboarding.repository.product.querydsl.ProductSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,Long>, ProductSearchRepository {
    @Query("SELECT p FROM Product p " +
            "JOIN ProductCategory pc ON pc.product.id = p.id " +
            "WHERE pc.category.id = :id")
    Page<Product> findProductsWithCategoryId(Pageable pageable, @Param("id") Long id);
}
