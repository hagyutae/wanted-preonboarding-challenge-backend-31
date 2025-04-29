package com.wanted.ecommerce.product.repository;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.dto.request.ProductReadAllRequest;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ProductSearchRepository {
    PageImpl<Product> findAllByRequest(ProductReadAllRequest request, Pageable pageable);

    List<Product> findRelatedProductsByCategoryId(Long categoryId);
}
