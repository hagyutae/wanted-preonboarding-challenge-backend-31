package sample.challengewanted.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.challengewanted.domain.product.entity.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
