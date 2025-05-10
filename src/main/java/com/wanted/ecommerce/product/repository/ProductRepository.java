package com.wanted.ecommerce.product.repository;

import com.wanted.ecommerce.product.domain.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearchRepository {

    Page<Product> findByCategoriesIdIn(List<Long> categoryIds, Pageable pageable);
}
