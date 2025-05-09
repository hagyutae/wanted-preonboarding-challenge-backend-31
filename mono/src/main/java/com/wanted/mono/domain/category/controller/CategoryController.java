package com.wanted.mono.domain.category.controller;

import com.wanted.mono.domain.category.entity.dto.CategoryDto;
import com.wanted.mono.domain.category.entity.dto.ProductCategoryDto;
import com.wanted.mono.domain.category.entity.dto.request.CategoryPagingRequest;
import com.wanted.mono.domain.category.entity.dto.response.CategoryProductsResponse;
import com.wanted.mono.domain.category.service.CategoryService;
import com.wanted.mono.domain.product.dto.request.ProductSearchRequest;
import com.wanted.mono.domain.product.dto.response.ProductSearchResponse;
import com.wanted.mono.domain.category.service.ProductCategoryService;
import com.wanted.mono.domain.product.service.ProductService;
import com.wanted.mono.global.common.CommonResponse;
import com.wanted.mono.global.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wanted.mono.global.message.MessageCode.CATEGORY_LIST_SUCCESS;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final MessageUtil messageUtil;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam(value = "level", required = false) Integer level) {
        List<CategoryDto> response = categoryService.findAllByLevel(level);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(response, messageUtil.get(CATEGORY_LIST_SUCCESS)));
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<?> findAllByCategoryId(
            @PathVariable("categoryId") Long categoryId,
            @ModelAttribute CategoryPagingRequest request) {

        CategoryDto categoryDto = categoryService.findById(categoryId);

        ProductSearchRequest productSearchRequest = ProductSearchRequest.builder()
                .page(request.getPage())
                .perPage(request.getPerPage())
                .sort(request.getSort())
                .category(List.of(categoryDto.getId()))
                .build();

        ProductSearchResponse productSearchResponse = productService.searchProduct(productSearchRequest);

        CategoryProductsResponse response = new CategoryProductsResponse(categoryDto, productSearchResponse.getItems(), productSearchResponse.getPagination());

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(response, messageUtil.get(CATEGORY_LIST_SUCCESS)));
    }
}
