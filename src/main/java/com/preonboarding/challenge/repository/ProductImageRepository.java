package com.preonboarding.challenge.repository;

import com.preonboarding.challenge.entity.ProductImage;
import com.preonboarding.challenge.repository.projection.ProductImageProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Query("SELECT i.id as id, i.url as url, i.altText as altText, i.isPrimary as isPrimary, " +
            "i.displayOrder as displayOrder, i.product.id as productId, i.option.id as optionId " +
            "FROM ProductImage i " +
            "WHERE i.product.id = :productId")
    List<ProductImageProjection> findImagesByProductId(@Param("productId") Long productId);

    @Query("SELECT i.id as id, i.url as url, i.altText as altText, i.isPrimary as isPrimary, " +
            "i.displayOrder as displayOrder, i.product.id as productId, i.option.id as optionId " +
            "FROM ProductImage i " +
            "WHERE i.option.id = :optionId")
    List<ProductImageProjection> findImagesByOptionId(@Param("optionId") Long optionId);
}