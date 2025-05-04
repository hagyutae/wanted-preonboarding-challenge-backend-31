package investLee.platform.ecommerce.repository;

import investLee.platform.ecommerce.domain.entity.ProductEntity;
import investLee.platform.ecommerce.repository.custom.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductRepositoryCustom {
    List<ProductEntity> findTop10ByOrderByCreatedAtDesc();

    @Query("""
    SELECT p FROM ProductEntity p
    JOIN ProductCategoryEntity pc ON pc.product = p
    WHERE pc.category.id = :categoryId
""")
    List<ProductEntity> findTop3ByCategory(@Param("categoryId") Long categoryId);

}