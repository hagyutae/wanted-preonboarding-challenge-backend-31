package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.domain.model.product.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagJpaRepository extends JpaRepository<ProductTag, Long> {
}
