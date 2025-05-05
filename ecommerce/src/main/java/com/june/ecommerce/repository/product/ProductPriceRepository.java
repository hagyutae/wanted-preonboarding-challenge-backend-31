package com.june.ecommerce.repository.product;

import com.june.ecommerce.domain.productprice.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Integer> {
}
