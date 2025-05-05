package sample.challengewanted.domain.product.repository;

import sample.challengewanted.api.controller.product.response.ProductResponse;
import sample.challengewanted.dto.product.ProductResponseV1;
import sample.challengewanted.dto.product.ProductPageResponse;
import sample.challengewanted.dto.product.ProductSearchCondition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<ProductPageResponse> searchProducts(ProductSearchCondition condition, Pageable pageable);

    ProductResponseV1 findProductById(Long id);
}
