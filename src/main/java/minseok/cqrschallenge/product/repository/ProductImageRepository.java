package minseok.cqrschallenge.product.repository;

import java.util.Optional;
import minseok.cqrschallenge.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findByProductIdAndIsPrimaryTrue(Long productId);
}
