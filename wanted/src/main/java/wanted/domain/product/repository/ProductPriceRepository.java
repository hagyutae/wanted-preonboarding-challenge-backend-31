package wanted.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.product.entity.ProductPrice;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    void deleteByProductId(Long productId);
}
