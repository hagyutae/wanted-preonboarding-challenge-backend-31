package com.wanted.ecommerce.product.repository;

import com.wanted.ecommerce.product.domain.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

}
