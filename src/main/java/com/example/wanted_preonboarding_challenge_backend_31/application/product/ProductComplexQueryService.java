package com.example.wanted_preonboarding_challenge_backend_31.application.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.query.ProductQueryRepository;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductSearchReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductSearchDataRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductSearchRes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductComplexQueryService {

    private final ProductQueryRepository productQueryRepository;

    public ProductSearchRes searchProducts(PaginationReq paginationReq, ProductSearchReq req) {
        List<ProductSearchDataRes> products = productQueryRepository.searchProducts(paginationReq, req);
        PaginationRes paginationRes = productQueryRepository.countSearchProducts(paginationReq, req);

        return new ProductSearchRes(products, paginationRes);
    }
}
