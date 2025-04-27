package com.wanted.ecommerce.product.repository;

import com.wanted.ecommerce.product.domain.ProductImage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    Optional<ProductImage> findByProductIdAndPrimaryTrue(Long productId);
}
