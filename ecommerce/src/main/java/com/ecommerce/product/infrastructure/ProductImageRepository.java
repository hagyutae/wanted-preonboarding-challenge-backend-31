package com.ecommerce.product.infrastructure;

import com.ecommerce.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    
    List<ProductImage> findByProductId(Long productId);
    
    List<ProductImage> findByProductIdOrderByDisplayOrderAsc(Long productId);
    
    Optional<ProductImage> findByProductIdAndIsPrimaryTrue(Long productId);
    
    List<ProductImage> findByOptionId(Long optionId);
    
    @Query("SELECT pi FROM ProductImage pi WHERE pi.product.id = :productId AND pi.option.id IS NULL")
    List<ProductImage> findCommonImagesByProductId(@Param("productId") Long productId);
    
    @Modifying
    @Query("UPDATE ProductImage pi SET pi.isPrimary = false WHERE pi.product.id = :productId AND pi.id <> :imageId")
    void unsetPrimaryForOtherImages(@Param("productId") Long productId, @Param("imageId") Long imageId);
    
    @Query("SELECT pi FROM ProductImage pi " +
           "WHERE pi.product.id = :productId " +
           "ORDER BY CASE WHEN pi.isPrimary = true THEN 0 ELSE 1 END, pi.displayOrder ASC")
    List<ProductImage> findProductImagesOrderedByPrimaryAndDisplayOrder(@Param("productId") Long productId);
    
    @Modifying
    @Query("DELETE FROM ProductImage pi WHERE pi.product.id = :productId")
    void deleteAllByProductId(@Param("productId") Long productId);
}
