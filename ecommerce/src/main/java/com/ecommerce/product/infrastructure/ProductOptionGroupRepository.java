package com.ecommerce.product.infrastructure;

import com.ecommerce.product.domain.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
    
    List<ProductOptionGroup> findByProductId(Long productId);
    
    List<ProductOptionGroup> findByProductIdOrderByDisplayOrderAsc(Long productId);
    
    @Query("SELECT pog FROM ProductOptionGroup pog LEFT JOIN FETCH pog.product WHERE pog.product.id = :productId")
    List<ProductOptionGroup> findByProductIdWithProduct(@Param("productId") Long productId);
    
    @Query("SELECT DISTINCT pog FROM ProductOptionGroup pog " +
           "LEFT JOIN FETCH pog.product p " +
           "LEFT JOIN FETCH p.seller " +
           "LEFT JOIN FETCH p.brand " +
           "WHERE pog.product.id = :productId " +
           "ORDER BY pog.displayOrder ASC")
    List<ProductOptionGroup> findByProductIdWithDetails(@Param("productId") Long productId);
    
    @Query(value = "SELECT pog.* FROM product_option_groups pog " +
                  "JOIN product_options po ON pog.id = po.option_group_id " +
                  "WHERE po.id = :optionId", 
           nativeQuery = true)
    ProductOptionGroup findByOptionId(@Param("optionId") Long optionId);
}
