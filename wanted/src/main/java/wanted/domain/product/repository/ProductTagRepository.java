package wanted.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.product.entity.ProductTag;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
    void deleteByProductId(Long productId);
}
