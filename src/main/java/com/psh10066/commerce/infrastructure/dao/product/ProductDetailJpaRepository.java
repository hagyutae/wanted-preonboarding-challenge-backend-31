package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.domain.model.product.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailJpaRepository extends JpaRepository<ProductDetail, Long> {
}
