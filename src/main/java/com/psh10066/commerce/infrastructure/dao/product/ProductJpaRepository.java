package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.domain.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductCustomRepository {
}
