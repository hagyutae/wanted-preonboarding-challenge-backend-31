package com.example.wanted_preonboarding_challenge_backend_31.application.category;

import com.example.wanted_preonboarding_challenge_backend_31.application.product.ProductComplexQueryService;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category.CategoryDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.request.CategoryProductReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.response.CategoryProductRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.response.CategorySearchRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductSearchReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductSearchRes;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryQueryService categoryQueryService;
    private final ProductComplexQueryService productComplexQueryService;
    private final CategoryComplexQueryService categoryComplexQueryService;

    public List<CategorySearchRes> search(int level) {
        List<CategorySearchRes> result = categoryQueryService.findAllByLevel(level);
        Map<Long, List<CategorySearchRes>> childCategories = categoryComplexQueryService.getChildCategoriesByParentId(
                level + 1);
        collectCategory(result, childCategories);
        return result;
    }

    private void collectCategory(List<CategorySearchRes> result, Map<Long, List<CategorySearchRes>> childMap) {
        if (result == null || result.isEmpty()) {
            return;
        }
        for (CategorySearchRes parent : result) {
            parent.setChildren(childMap.get(parent.getId()));
            collectCategory(parent.getChildren(), childMap);
        }
    }

    public CategoryProductRes searchProduct(PaginationReq paginationReq, Long categoryId, CategoryProductReq req) {
        CategoryDetailDto categoryDetail = categoryQueryService.getCategoryDetailById(categoryId);

        List<Long> categoryIds = categoryQueryService.getSubcategoryIds(categoryId, req.includeSubcategories());
        ProductSearchReq productSearchReq = new ProductSearchReq(req.sort(), null, null, null, categoryIds, null, null,
                null, null);
        ProductSearchRes productSearchRes = productComplexQueryService.searchProducts(paginationReq, productSearchReq);

        return new CategoryProductRes(categoryDetail, productSearchRes.items(), productSearchRes.pagination());
    }
}
