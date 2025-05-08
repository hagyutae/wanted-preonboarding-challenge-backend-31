package com.june.ecommerce.repository.product;

import com.june.ecommerce.domain.productimage.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    Collection<ProductImage> findByProductId(int productId);
}
