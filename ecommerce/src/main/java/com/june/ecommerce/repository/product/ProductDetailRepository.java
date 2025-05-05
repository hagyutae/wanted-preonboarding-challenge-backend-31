package com.june.ecommerce.repository.product;

import com.june.ecommerce.domain.productdetail.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
}
