package com.psh10066.commerce.api.controller;

import com.psh10066.commerce.api.common.ApiResponse;
import com.psh10066.commerce.api.dto.request.CreateProductOptionRequest;
import com.psh10066.commerce.api.dto.request.UpdateProductOptionRequest;
import com.psh10066.commerce.api.dto.response.CreateProductOptionResponse;
import com.psh10066.commerce.api.dto.response.UpdateProductOptionResponse;
import com.psh10066.commerce.domain.service.ProductOptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products/{id}/options")
@RequiredArgsConstructor
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateProductOptionResponse> createProductOption(
        @PathVariable Long id,
        @Valid @RequestBody CreateProductOptionRequest request
    ) {
        CreateProductOptionResponse response = productOptionService.createProductOption(id, request);
        return ApiResponse.success(response, "상품 옵션이 성공적으로 추가되었습니다.");
    }

    @PutMapping("/{optionId}")
    public ApiResponse<UpdateProductOptionResponse> updateProductOption(
        @PathVariable Long id,
        @PathVariable Long optionId,
        @Valid @RequestBody UpdateProductOptionRequest request
    ) {
        UpdateProductOptionResponse response = productOptionService.updateProductOption(id, optionId, request);
        return ApiResponse.success(response, "상품 옵션이 성공적으로 수정되었습니다.");
    }
}
