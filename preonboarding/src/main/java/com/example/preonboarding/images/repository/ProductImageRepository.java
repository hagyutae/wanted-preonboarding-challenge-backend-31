package com.example.preonboarding.images.repository;

import com.example.preonboarding.images.domain.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductImageRepository extends JpaRepository<ProductImages,Long> {
    @Modifying
    @Query("UPDATE ProductImages pi SET pi.isPrimary = false WHERE pi.products.id = :productId AND pi.isPrimary = true")
    void clearPrimaryImageByProductId(@Param("productId") Long productId);
}
