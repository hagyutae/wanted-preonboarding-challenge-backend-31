package kr.co.wanted.backend31.repository.product;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.wanted.backend31.common.model.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
        @Query("SELECT p FROM Product p " +
                        "WHERE (:status IS NULL OR p.status = :status) " +
                        "AND (:seller IS NULL OR p.seller.id = :seller) " +
                        "AND (:brand IS NULL OR p.brand.id = :brand) " +
                        "AND (:minPrice IS NULL OR p.price.salePrice >= :minPrice) " +
                        "AND (:maxPrice IS NULL OR p.price.salePrice <= :maxPrice) ")
        Page<Product> searchPage(Optional<String> status, Optional<Long> seller, Optional<Long> brand,
                        Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice, Pageable pageable);

}
