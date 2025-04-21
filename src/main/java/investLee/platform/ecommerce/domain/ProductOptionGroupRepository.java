package investLee.platform.ecommerce.domain;

import investLee.platform.ecommerce.domain.entity.ProductOptionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroupEntity, Long> {
}