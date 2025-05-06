package com.shopping.mall.product.repository;

import com.shopping.mall.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT DISTINCT p 
        FROM Product p
        LEFT JOIN FETCH p.productTags pt
        LEFT JOIN p.productPrice pp
        WHERE (:status IS NULL OR p.status = :status)
          AND (:minPrice IS NULL OR pp.basePrice >= :minPrice)
          AND (:maxPrice IS NULL OR pp.basePrice <= :maxPrice)
          AND (:sellerId IS NULL OR p.seller.id = :sellerId)
          AND (:brandId IS NULL OR p.brand.id = :brandId)
          AND (:inStock IS NULL OR EXISTS (
              SELECT 1 FROM ProductOption po
              WHERE po.optionGroup.product = p
                AND po.stock > 0
          ))
          AND (:tagIds IS NULL OR pt.tag.id IN :tagIds)
        ORDER BY p.createdAt DESC
    """)
    List<Product> findByCondition(
            @Param("status") String status,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("sellerId") Long sellerId,
            @Param("brandId") Long brandId,
            @Param("inStock") Boolean inStock,
            @Param("tagIds") List<Long> tagIds
    );

    @Query("""
        SELECT p FROM Product p
        JOIN ProductTag pt ON pt.product = p
        WHERE (:tagIds IS NULL OR pt.tag.id IN :tagIds)
    """)
    List<Product> findByTags(@Param("tagIds") List<Long> tagIds);

    @Query("""
        SELECT p FROM Product p
        LEFT JOIN FETCH p.productDetail
        LEFT JOIN FETCH p.productPrice
        LEFT JOIN FETCH p.optionGroups og
        LEFT JOIN FETCH og.options
        LEFT JOIN FETCH p.productImages
        LEFT JOIN FETCH p.productTags pt
        LEFT JOIN FETCH pt.tag
        LEFT JOIN FETCH p.productCategories pc
        LEFT JOIN FETCH pc.category
        WHERE p.id = :productId
    """)
    Optional<Product> findByIdWithAll(@Param("productId") Long productId);

}
