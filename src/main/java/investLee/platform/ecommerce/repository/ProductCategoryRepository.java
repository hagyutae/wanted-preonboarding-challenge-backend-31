package investLee.platform.ecommerce.repository;

import investLee.platform.ecommerce.domain.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
    List<ProductCategoryEntity> findByProductId(Long productId);

    @Modifying
    @Query("DELETE FROM ProductCategoryEntity pc WHERE pc.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}