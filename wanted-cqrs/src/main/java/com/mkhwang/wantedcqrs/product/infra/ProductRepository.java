package com.mkhwang.wantedcqrs.product.infra;

import com.mkhwang.wantedcqrs.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
