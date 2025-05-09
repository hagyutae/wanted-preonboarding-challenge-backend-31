package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Product;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.ProductSummaryDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.ProductsByCategoryCondition;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.ProductsSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<ProductSummaryDto> findByCondition(ProductsSearchCondition condition, Pageable pageable);
    Page<ProductSummaryDto> findByCondition(ProductsByCategoryCondition condition, Pageable pageable);


    Product findWithSellerBrandPriceDetailById(Long productId);
}
