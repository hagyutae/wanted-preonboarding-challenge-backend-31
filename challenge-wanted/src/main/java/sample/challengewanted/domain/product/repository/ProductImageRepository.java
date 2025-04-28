package sample.challengewanted.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.challengewanted.domain.product.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
