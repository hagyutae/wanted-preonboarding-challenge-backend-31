package com.wanted.ecommerce.product.repository;

import com.wanted.ecommerce.product.domain.ProductPrice;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

    Optional<ProductPrice> findByProductId(Long productId);
}
