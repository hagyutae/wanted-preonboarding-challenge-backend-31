package wanted.shop.product.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.product.domain.entity.Product;

public interface ProductDataRepository extends JpaRepository<Product, Long> {
}
