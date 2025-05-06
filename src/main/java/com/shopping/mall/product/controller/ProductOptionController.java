package com.shopping.mall.product.controller;

import com.shopping.mall.common.response.ApiResponse;
import com.shopping.mall.product.dto.request.ProductOptionCreateRequest;
import com.shopping.mall.product.dto.request.ProductOptionUpdateRequest;
import com.shopping.mall.product.service.ProductOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @PostMapping("/{productId}/options")
    public ResponseEntity<?> addOptionGroup(
            @PathVariable Long productId,
            @RequestBody ProductOptionCreateRequest request) {

        productOptionService.addOptionGroup(productId, request);
        return ResponseEntity.ok(ApiResponse.success(null, "상품 옵션 그룹이 성공적으로 추가되었습니다."));
    }

    @PutMapping("/{productId}/options/{optionId}")
    public ResponseEntity<?> updateOption(
            @PathVariable Long productId,
            @PathVariable Long optionId,
            @RequestBody ProductOptionUpdateRequest request) {

        productOptionService.updateOption(optionId, request);
        return ResponseEntity.ok(ApiResponse.success(null, "상품 옵션이 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/{productId}/options/{optionId}")
    public ResponseEntity<?> deleteOption(
            @PathVariable Long productId,
            @PathVariable Long optionId) {

        productOptionService.deleteOption(productId, optionId);

        return ResponseEntity.ok(ApiResponse.success(null, "상품 옵션이 성공적으로 삭제되었습니다."));
    }
}
