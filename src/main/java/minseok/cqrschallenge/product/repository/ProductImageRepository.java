package minseok.cqrschallenge.product.repository;

import minseok.cqrschallenge.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    ProductImage findByProductIdAndIsPrimaryTrue(Long productId);
}
