package investLee.platform.ecommerce.repository;

import investLee.platform.ecommerce.domain.entity.ProductTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTagEntity, Long> {
}