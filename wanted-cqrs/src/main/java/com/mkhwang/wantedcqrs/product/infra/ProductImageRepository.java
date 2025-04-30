package com.mkhwang.wantedcqrs.product.infra;

import com.mkhwang.wantedcqrs.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
  List<ProductImage> findByProductId(Long id);
}
