package wanted.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wanted.domain.product.dto.ProductSearchCondition;
import wanted.domain.product.entity.Product;

public interface ProductQueryRepository {
    Page<Product> search(ProductSearchCondition condition, Pageable pageable);
}

