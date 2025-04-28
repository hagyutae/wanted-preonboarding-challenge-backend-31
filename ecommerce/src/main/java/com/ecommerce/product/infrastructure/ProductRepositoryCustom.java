package com.ecommerce.product.infrastructure;

import com.ecommerce.product.application.dto.req.ProductSearchRequest;
import com.ecommerce.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    /**
     * 검색 요청에 따라 상품 목록을 조회합니다.
     *
     * @param request  검색 요청 DTO
     * @param pageable 페이징 정보
     * @return 페이징 처리된 상품 목록
     */
    Page<Product> findBySearchRequest(ProductSearchRequest request, Pageable pageable);
}
