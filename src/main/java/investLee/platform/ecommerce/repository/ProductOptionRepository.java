package investLee.platform.ecommerce.repository;

import investLee.platform.ecommerce.domain.entity.ProductOptionEntity;
import investLee.platform.ecommerce.domain.entity.ProductOptionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOptionEntity, Long> {
}