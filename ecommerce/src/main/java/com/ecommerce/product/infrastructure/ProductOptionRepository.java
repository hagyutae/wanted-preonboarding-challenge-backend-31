package com.ecommerce.product.infrastructure;

import com.ecommerce.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    
    List<ProductOption> findByOptionGroupId(Long optionGroupId);
    
    List<ProductOption> findByOptionGroupIdOrderByDisplayOrderAsc(Long optionGroupId);
    
    @Query("SELECT po FROM ProductOption po WHERE po.optionGroup.product.id = :productId")
    List<ProductOption> findByProductId(@Param("productId") Long productId);
    
    @Query("SELECT po FROM ProductOption po WHERE po.sku = :sku")
    Optional<ProductOption> findBySku(@Param("sku") String sku);
    
    @Query("SELECT po FROM ProductOption po WHERE po.stock > 0 AND po.optionGroup.product.id = :productId")
    List<ProductOption> findAvailableOptionsByProductId(@Param("productId") Long productId);
    
    @Modifying
    @Query("UPDATE ProductOption po SET po.stock = po.stock - :quantity WHERE po.id = :optionId AND po.stock >= :quantity")
    int decreaseStock(@Param("optionId") Long optionId, @Param("quantity") int quantity);
    
    @Query("SELECT SUM(po.stock) FROM ProductOption po WHERE po.optionGroup.product.id = :productId")
    Integer getTotalStockByProductId(@Param("productId") Long productId);
    
    @Query(value = "SELECT DISTINCT p.id FROM products p " +
                  "JOIN product_option_groups pog ON p.id = pog.product_id " +
                  "JOIN product_options po ON pog.id = po.option_group_id " +
                  "WHERE po.stock > 0", 
           nativeQuery = true)
    List<Long> findProductIdsWithAvailableStock();
}
