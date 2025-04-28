package sample.challengewanted.domain.product.repository;

import sample.challengewanted.api.controller.product.response.ProductResponse;
import sample.challengewanted.dto.ProductSearchCondition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<ProductResponse> searchProducts(ProductSearchCondition condition, Pageable pageable);
}
