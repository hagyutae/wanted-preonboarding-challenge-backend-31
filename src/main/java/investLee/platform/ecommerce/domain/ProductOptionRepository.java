package investLee.platform.ecommerce.domain;

import investLee.platform.ecommerce.domain.entity.ProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOptionEntity, Long> {
}