package com.example.wanted_preonboarding_challenge_backend_31.application.main;

import com.example.wanted_preonboarding_challenge_backend_31.application.category.CategoryComplexQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.application.category.CategoryQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.application.product.ProductComplexQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.application.review.ReviewQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.Category;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category.CategoryFeaturedDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductSearchDataDto;
import com.example.wanted_preonboarding_challenge_backend_31.web.main.dto.response.MainSearchRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductSearchReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductSearchRes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MainService {

    private final ProductComplexQueryService productComplexQueryService;
    private final ReviewQueryService reviewQueryService;
    private final CategoryComplexQueryService categoryComplexQueryService;
    private final CategoryQueryService categoryQueryService;

    public MainSearchRes search() {
        PaginationReq paginationReq = new PaginationReq(1, 5);

        List<ProductSearchDataDto> newProductSearchData = getNewProductSearchData(paginationReq);
        List<ProductSearchDataDto> popularProductSearchData = getPopularProductSearchData(paginationReq);
        List<CategoryFeaturedDto> featuredCategories = getFeaturedCategories(paginationReq.perPage());

        return new MainSearchRes(newProductSearchData, popularProductSearchData, featuredCategories);
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

    private List<CategoryFeaturedDto> getFeaturedCategories(int limit) {
        Map<Long, Long> productCountMap = categoryComplexQueryService.getFeaturedCategoryMap(limit);
        List<Category> categories = categoryQueryService.findAllByIds(productCountMap.keySet());
        return categories.stream()
                .map(category -> new CategoryFeaturedDto(
                        category.getId(),
                        category.getName(),
                        category.getSlug(),
                        category.getImageUrl(),
                        productCountMap.get(category.getId())
                ))
                .sorted(Comparator.comparing(CategoryFeaturedDto::productCount).reversed())
                .toList();
    }
}
