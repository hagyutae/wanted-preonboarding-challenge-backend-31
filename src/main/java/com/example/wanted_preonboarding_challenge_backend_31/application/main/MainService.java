package com.example.wanted_preonboarding_challenge_backend_31.application.main;

import com.example.wanted_preonboarding_challenge_backend_31.application.product.ProductComplexQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.application.review.ReviewQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductSearchDataDto;
import com.example.wanted_preonboarding_challenge_backend_31.web.main.dto.response.MainSearchRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductSearchReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductSearchRes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MainService {

    private final ProductComplexQueryService productComplexQueryService;
    private final ReviewQueryService reviewQueryService;

    public MainSearchRes search() {
        PaginationReq paginationReq = new PaginationReq(1, 5);

        List<ProductSearchDataDto> newProductSearchData = getNewProductSearchData(paginationReq);
        List<ProductSearchDataDto> popularProductSearchData = getPopularProductSearchData(paginationReq);

        return new MainSearchRes(newProductSearchData, popularProductSearchData, null);
    }

    private List<ProductSearchDataDto> getNewProductSearchData(PaginationReq paginationReq) {
        ProductSearchReq newProductSearchReq = new ProductSearchReq("createdAt:desc", null, null, null, null, null,
                null, null, null, null);
        return productComplexQueryService.searchProducts(paginationReq, newProductSearchReq).items();
    }

    private List<ProductSearchDataDto> getPopularProductSearchData(PaginationReq paginationReq) {
        List<Long> popularProductIds = reviewQueryService.getPopularProductIds(paginationReq.perPage());
        ProductSearchReq popularProductSearchReq = new ProductSearchReq("createdAt:desc", null, null, null, null, null,
                null, null, null, popularProductIds);
        ProductSearchRes productSearchRes = productComplexQueryService.searchProducts(paginationReq,
                popularProductSearchReq);
        List<ProductSearchDataDto> popularProductSearchData = new ArrayList<>(productSearchRes.items());

        popularProductSearchData
                .sort(Comparator.comparing(ProductSearchDataDto::rating).reversed());

        return popularProductSearchData;
    }
}
