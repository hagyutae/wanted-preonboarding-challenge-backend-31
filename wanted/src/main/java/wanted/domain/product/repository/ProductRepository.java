package wanted.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
