package wanted.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.product.entity.ProductOptionGroup;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
    void deleteByProductId(Long productId);
}
