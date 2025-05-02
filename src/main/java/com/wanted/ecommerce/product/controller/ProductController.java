package com.wanted.ecommerce.product.controller;

import com.wanted.ecommerce.common.constants.MessageConstants;
import com.wanted.ecommerce.common.response.ApiResponse;
import com.wanted.ecommerce.common.response.PaginationResponse;
import com.wanted.ecommerce.product.dto.request.ProductCreateRequest;
import com.wanted.ecommerce.product.dto.request.ProductImageRequest;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.dto.request.ProductReadAllRequest;
import com.wanted.ecommerce.product.dto.response.ProductDetailResponse;
import com.wanted.ecommerce.product.dto.response.ProductListResponse;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import com.wanted.ecommerce.product.dto.response.ProductResponse;
import com.wanted.ecommerce.product.dto.response.ProductUpdateResponse;
import com.wanted.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
        @Valid @RequestBody ProductCreateRequest productCreateRequest
    ) {
        ApiResponse<ProductResponse> response = ApiResponse.success(
            productService.create(productCreateRequest),
            MessageConstants.CREATED_PRODUCTS.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationResponse<ProductListResponse>>> getAllProduct(
        @Valid @ModelAttribute ProductReadAllRequest request) {
        Page<ProductListResponse> results = productService.readAll(request);
        PaginationResponse<ProductListResponse> response = PaginationResponse.of(results);
        return ResponseEntity.ok(
            ApiResponse.success(response, MessageConstants.FUNDED_ALL_PRODUCTS.getMessage()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductById(
        @PathVariable Long id
    ) {
        ProductDetailResponse response = productService.readDetail(id);
        return ResponseEntity.ok(
            ApiResponse.success(response, MessageConstants.FUNDED_PRODUCT_DETAIL.getMessage()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductUpdateResponse>> updateProduct(
        @PathVariable Long id,
        @Valid @RequestBody ProductCreateRequest productCreateRequest
    ) {
        ProductUpdateResponse response = productService.update(id, productCreateRequest);
        return ResponseEntity.ok(
            ApiResponse.success(response, MessageConstants.UPDATED_PRODUCT.getMessage()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteProduct(
        @PathVariable Long id
    ) {
        productService.delete(id);
        return ResponseEntity.ok(
            ApiResponse.success(null, MessageConstants.DELETED_PRODUCT.getMessage()));
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<ApiResponse<ProductOptionResponse>> addProductOptions(
        @PathVariable Long id,
        @Valid @RequestBody ProductOptionRequest optionRequest
    ) {
        ProductOptionResponse response = productService.addProductOption(id, optionRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(response, MessageConstants.CREATED_OPTION.getMessage()));
    }

    @PutMapping("/{id}/options/{optionId}")
    public ResponseEntity<ApiResponse<ProductOptionResponse>> updateProductOptions(
        @PathVariable Long id,
        @PathVariable Long optionId,
        @Valid @RequestBody ProductOptionRequest updateRequest
    ) {
        ProductOptionResponse response = productService.updateProductOption(id, optionId,
            updateRequest);
        return ResponseEntity.ok(
            ApiResponse.success(response, MessageConstants.CREATED_OPTION.getMessage()));
    }

    @DeleteMapping("/{id}/options/{optionId}")
    public ResponseEntity<Object> deleteProductOptions(
        @PathVariable Long id,
        @PathVariable Long optionId
    ) {
        return null;
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<Object> addProductImages(
        @PathVariable Long id,
        @Valid @RequestBody ProductImageRequest imageRequest
    ) {
        return null;
    }
}
