package com.june.ecommerce.repository.product;

import com.june.ecommerce.domain.productprice.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Integer> {
    Optional<ProductPrice> findByProductId(int productId);
}
