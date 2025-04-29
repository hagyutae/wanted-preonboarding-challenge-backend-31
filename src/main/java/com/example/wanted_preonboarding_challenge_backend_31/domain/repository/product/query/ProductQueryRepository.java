package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.query;

import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductSearchReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductDetailRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductSearchDataRes;
import java.util.List;

public interface ProductQueryRepository {

    List<ProductSearchDataRes> searchProducts(PaginationReq paginationReq, ProductSearchReq req);

    PaginationRes countSearchProducts(PaginationReq paginationReq, ProductSearchReq req);

    ProductDetailRes detailProduct(Long productId);
}
