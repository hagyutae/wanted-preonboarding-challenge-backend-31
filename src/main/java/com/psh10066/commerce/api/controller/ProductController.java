package com.psh10066.commerce.api.controller;

import com.psh10066.commerce.api.common.ApiResponse;
import com.psh10066.commerce.api.common.PaginationResponse;
import com.psh10066.commerce.api.dto.request.CreateProductRequest;
import com.psh10066.commerce.api.dto.request.GetAllProductsRequest;
import com.psh10066.commerce.api.dto.request.UpdateProductRequest;
import com.psh10066.commerce.api.dto.response.CreateProductResponse;
import com.psh10066.commerce.api.dto.response.GetAllProductsResponse;
import com.psh10066.commerce.api.dto.response.GetProductDetailResponse;
import com.psh10066.commerce.api.dto.response.UpdateProductResponse;
import com.psh10066.commerce.domain.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateProductResponse> createProduct(
        @Valid @RequestBody CreateProductRequest request
    ) {
        CreateProductResponse response = productService.createProduct(request);
        return ApiResponse.success(response, "상품이 성공적으로 등록되었습니다.");
    }

    @GetMapping
    public ApiResponse<PaginationResponse<GetAllProductsResponse>> getAllProducts(GetAllProductsRequest request) {

        PaginationResponse<GetAllProductsResponse> response = productService.getAllProducts(request);
        return ApiResponse.success(response, "상품 목록을 성공적으로 조회했습니다.");
    }

    @GetMapping("/{id}")
    public ApiResponse<GetProductDetailResponse> getProductDetail(@PathVariable Long id) {

        GetProductDetailResponse response = productService.getProductDetail(id);
        return ApiResponse.success(response, "상품 상세 정보를 성공적으로 조회했습니다.");
    }

    @PutMapping("/{id}")
    public ApiResponse<UpdateProductResponse> updateProduct(
        @PathVariable Long id,
        @Valid @RequestBody UpdateProductRequest request
    ) {
        UpdateProductResponse response = productService.updateProduct(id, request);
        return ApiResponse.success(response, "상품이 성공적으로 수정되었습니다.");
    }
}
