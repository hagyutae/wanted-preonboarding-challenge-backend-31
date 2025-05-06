package com.wanted.ecommerce.product.repository;

import com.wanted.ecommerce.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearchRepository {

}
