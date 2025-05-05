package sample.challengewanted.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.challengewanted.domain.product.entity.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
