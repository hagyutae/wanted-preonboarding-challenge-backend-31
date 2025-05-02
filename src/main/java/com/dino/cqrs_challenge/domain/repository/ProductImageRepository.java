package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllByProductIdInAndIsPrimary(List<Long> productIds, Boolean isPrimary);
}
