package sample.challengewanted.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.challengewanted.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

}

