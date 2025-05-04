package investLee.platform.ecommerce.repository;

import investLee.platform.ecommerce.domain.entity.ProductDetailEntity;
import investLee.platform.ecommerce.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity, Long> {
    Optional<ProductDetailEntity> findByProduct(ProductEntity product);
}