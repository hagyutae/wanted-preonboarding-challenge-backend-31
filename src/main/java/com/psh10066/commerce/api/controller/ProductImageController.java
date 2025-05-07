package com.psh10066.commerce.api.controller;

import com.psh10066.commerce.api.common.ApiResponse;
import com.psh10066.commerce.api.dto.request.CreateProductImageRequest;
import com.psh10066.commerce.api.dto.response.CreateProductImageResponse;
import com.psh10066.commerce.domain.service.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/{id}/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateProductImageResponse> createProductImage(
        @PathVariable Long id,
        @Valid @RequestBody CreateProductImageRequest request
    ) {
        CreateProductImageResponse response = productImageService.createProductImage(id, request);
        return ApiResponse.success(response, "상품 이미지가 성공적으로 추가되었습니다.");
    }
}
