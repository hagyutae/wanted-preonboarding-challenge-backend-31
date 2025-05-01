package com.sandro.wanted_shop.product.persistence;

import com.sandro.wanted_shop.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
