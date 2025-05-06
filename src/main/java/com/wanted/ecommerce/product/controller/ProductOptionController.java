package com.wanted.ecommerce.product.controller;

import com.wanted.ecommerce.common.constants.MessageConstants;
import com.wanted.ecommerce.common.response.ApiResponse;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import com.wanted.ecommerce.product.service.ProductOptionService;
import com.wanted.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/{id}/options")
@RequiredArgsConstructor
public class ProductOptionController {
    private final ProductService productService;
    private final ProductOptionService optionService;

    @PostMapping()
    public ResponseEntity<ApiResponse<ProductOptionResponse>> addProductOptions(
        @PathVariable Long id,
        @Valid @RequestBody ProductOptionRequest optionRequest
    ) {
        Product product = productService.getProductById(id);
        ProductOptionResponse response = optionService.addProductOption(product, optionRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(response, MessageConstants.CREATED_OPTION.getMessage()));
    }

    @PutMapping("/{optionId}")
    public ResponseEntity<ApiResponse<ProductOptionResponse>> updateProductOptions(
        @PathVariable Long id,
        @PathVariable Long optionId,
        @Valid @RequestBody ProductOptionRequest updateRequest
    ) {
        ProductOptionResponse response = optionService.updateProductOption(id, optionId,
            updateRequest);
        return ResponseEntity.ok(
            ApiResponse.success(response, MessageConstants.UPDATED_OPTION.getMessage()));
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Object> deleteProductOptions(
        @PathVariable Long id,
        @PathVariable Long optionId
    ) {
        optionService.deleteProductOption(id, optionId);
        return ResponseEntity.ok(
            ApiResponse.success(null, MessageConstants.DELETED_OPTION.getMessage()));
    }
}
