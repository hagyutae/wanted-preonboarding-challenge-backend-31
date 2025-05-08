package cqrs.precourse.repository;

import cqrs.precourse.domain.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}
