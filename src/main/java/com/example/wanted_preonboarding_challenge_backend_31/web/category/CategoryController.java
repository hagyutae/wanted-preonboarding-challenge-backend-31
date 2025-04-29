package com.example.wanted_preonboarding_challenge_backend_31.web.category;

import static com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.CategorySuccessType.CATEGORY_SEARCH;
import static com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.CategorySuccessType.CATEGORY_SEARCH_PRODUCT;

import com.example.wanted_preonboarding_challenge_backend_31.application.category.CategoryService;
import com.example.wanted_preonboarding_challenge_backend_31.common.dto.SuccessRes;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.request.CategoryProductReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.response.CategoryProductRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.response.CategorySearchRes;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public SuccessRes<CategorySearchRes> search(
            @RequestParam(name = "level", defaultValue = "1") @Min(1) @Max(3) int level
    ) {
        return SuccessRes.of(CATEGORY_SEARCH, categoryService.search(level));
    }

    @GetMapping("/{id}/products")
    public SuccessRes<CategoryProductRes> searchProduct(
            @RequestParam(name = "page", defaultValue = "1") @Min(1) int page,
            @RequestParam(name = "perPage", defaultValue = "10") @Min(1) int perPage,
            @PathVariable("id") Long categoryId,
            @ModelAttribute @Validated CategoryProductReq categoryProductReq
    ) {
        PaginationReq paginationReq = new PaginationReq(page, perPage);
        return SuccessRes.of(CATEGORY_SEARCH_PRODUCT,
                categoryService.searchProduct(paginationReq, categoryId, categoryProductReq));
    }
}
