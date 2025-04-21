package investLee.platform.ecommerce.domain;

import investLee.platform.ecommerce.domain.entity.ProductPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPriceEntity, Long> {
}