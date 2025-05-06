package minseok.cqrschallenge.product.repository;

import minseok.cqrschallenge.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {


}
