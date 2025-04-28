package com.ecommerce.product.infrastructure;

import com.ecommerce.product.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    
    List<ProductCategory> findByProductId(Long productId);
    
    List<ProductCategory> findByCategoryId(Long categoryId);
    
    Optional<ProductCategory> findByProductIdAndCategoryId(Long productId, Long categoryId);
    
    List<ProductCategory> findByIsPrimaryTrue();
    
    @Query("SELECT pc FROM ProductCategory pc WHERE pc.product.id = :productId AND pc.isPrimary = true")
    Optional<ProductCategory> findPrimaryCategoryByProductId(@Param("productId") Long productId);
    
    @Query("SELECT pc.category.id FROM ProductCategory pc WHERE pc.product.id = :productId")
    List<Long> findCategoryIdsByProductId(@Param("productId") Long productId);
    
    @Query("SELECT DISTINCT pc.product.id FROM ProductCategory pc WHERE pc.category.id IN :categoryIds")
    List<Long> findProductIdsByCategoryIds(@Param("categoryIds") List<Long> categoryIds);
    
    @Query(value = "SELECT p.id FROM products p " +
                  "JOIN product_categories pc ON p.id = pc.product_id " +
                  "WHERE pc.category_id IN :categoryIds " +
                  "GROUP BY p.id " +
                  "HAVING COUNT(DISTINCT pc.category_id) = :categoryCount", 
           nativeQuery = true)
    List<Long> findProductIdsByAllCategories(@Param("categoryIds") List<Long> categoryIds, 
                                           @Param("categoryCount") int categoryCount);
}
