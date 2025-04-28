package com.ecommerce.product.infrastructure;

import com.ecommerce.product.domain.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    
    Optional<ProductPrice> findByProductId(Long productId);
    
    List<ProductPrice> findByCurrency(String currency);
    
    @Query("SELECT pp FROM ProductPrice pp WHERE pp.basePrice BETWEEN :minPrice AND :maxPrice")
    List<ProductPrice> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT pp FROM ProductPrice pp WHERE pp.salePrice IS NOT NULL AND pp.basePrice > pp.salePrice")
    List<ProductPrice> findDiscountedProducts();
    
    @Query(value = "SELECT * FROM product_prices WHERE (base_price - sale_price) / base_price >= :discountRate", 
           nativeQuery = true)
    List<ProductPrice> findByMinimumDiscountRate(@Param("discountRate") BigDecimal discountRate);
}
