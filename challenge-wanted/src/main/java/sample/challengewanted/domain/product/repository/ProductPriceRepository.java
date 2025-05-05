package sample.challengewanted.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.challengewanted.domain.product.entity.ProductPrice;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}
