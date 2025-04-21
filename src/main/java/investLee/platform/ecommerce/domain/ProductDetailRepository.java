package investLee.platform.ecommerce.domain;

import investLee.platform.ecommerce.domain.entity.ProductDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity, Long> {
}