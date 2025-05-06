package minseok.cqrschallenge.product.repository;

import minseok.cqrschallenge.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Product> findWithFilters(
            String status, Integer minPrice, Integer maxPrice, 
            String category, Integer seller, Integer brand, 
            Boolean inStock, String search, Pageable pageable);
}