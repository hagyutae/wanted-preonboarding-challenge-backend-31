package com.wanted.ecommerce.product.controller;

import com.wanted.ecommerce.common.constants.MessageConstants;
import com.wanted.ecommerce.common.response.ApiResponse;
import com.wanted.ecommerce.product.dto.request.ProductImageRequest;
import com.wanted.ecommerce.product.dto.response.ProductImageCreateResponse;
import com.wanted.ecommerce.product.service.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/{id}/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService imageService;

    @PostMapping()
    public ResponseEntity<ApiResponse<ProductImageCreateResponse>> addProductImage(
        @PathVariable Long id,
        @Valid @RequestBody ProductImageRequest imageRequest
    ) {
        ProductImageCreateResponse response = imageService.createProductImage(id, imageRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(response, MessageConstants.CREATED_IMAGE.getMessage()));
    }
}
