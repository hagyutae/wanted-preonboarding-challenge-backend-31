package wanted.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.product.entity.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    void deleteByProductId(Long productId);
}
