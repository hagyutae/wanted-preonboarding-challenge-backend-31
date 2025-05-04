package investLee.platform.ecommerce.repository;

import investLee.platform.ecommerce.domain.entity.ProductEntity;
import investLee.platform.ecommerce.domain.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
    List<ProductImageEntity> findByProductId(Long productId);
    Optional<ProductImageEntity> findFirstByProductIdAndIsPrimaryTrueOrderByDisplayOrderAsc(Long productId);
}