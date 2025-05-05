package wanted.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.product.entity.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
