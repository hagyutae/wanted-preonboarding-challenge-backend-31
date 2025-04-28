package investLee.platform.ecommerce.domain;

import investLee.platform.ecommerce.domain.entity.ProductEntity;
import investLee.platform.ecommerce.domain.entity.ProductOptionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroupEntity, Long> {
    List<ProductOptionGroupEntity> findByProduct(ProductEntity product);
}