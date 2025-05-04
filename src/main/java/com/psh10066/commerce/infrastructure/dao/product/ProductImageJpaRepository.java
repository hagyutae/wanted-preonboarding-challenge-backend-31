package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.domain.model.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageJpaRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllByProductId(Long productId);
}
