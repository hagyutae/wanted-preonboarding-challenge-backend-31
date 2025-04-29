package com.psh10066.commerce.api.controller;

import com.psh10066.commerce.api.common.ApiResponse;
import com.psh10066.commerce.api.dto.request.CreateProductRequest;
import com.psh10066.commerce.api.dto.response.CreateProductResponse;
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
}
