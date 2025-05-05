package com.example.cqrsapp.product.controller;

import com.example.cqrsapp.common.dto.ProductSummaryItem;
import com.example.cqrsapp.common.response.APIDataResponse;
import com.example.cqrsapp.common.response.PageResponseDto;
import com.example.cqrsapp.product.dto.requset.RegisterProductDto;
import com.example.cqrsapp.product.dto.response.ProductResponse;
import com.example.cqrsapp.product.dto.response.RegisterProductResponseDto;
import com.example.cqrsapp.product.repository.SearchParm;
import com.example.cqrsapp.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    APIDataResponse<PageResponseDto<ProductSummaryItem>> searchProducts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @ModelAttribute SearchParm searchParm) {
        PageResponseDto<ProductSummaryItem> products = productService.searchProduct(searchParm, pageable);
        return APIDataResponse.success(products, "상품 목록을 성공적으로 조회했습니다.");
    }

    @GetMapping("/{productId}")
    APIDataResponse<ProductResponse> getProduct(@PathVariable("productId") Long productId) {
        return APIDataResponse.success(productService.getProduct(productId), "상품 상세 정보를 성공적으로 조회했습니다.");
    }

    @PostMapping("")
    APIDataResponse<RegisterProductResponseDto> createProduct(@RequestBody RegisterProductDto dto) {
        RegisterProductResponseDto result = productService.createProduct(dto);
        return APIDataResponse.success(result, "상품이 성공적으로 등록되었습니다.");
    }
}
