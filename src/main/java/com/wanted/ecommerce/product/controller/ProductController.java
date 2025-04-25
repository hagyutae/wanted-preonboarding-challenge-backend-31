package com.wanted.ecommerce.product.controller;

import com.wanted.ecommerce.product.dto.request.ProductCreateRequest;
import com.wanted.ecommerce.product.dto.request.ProductImageRequest;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.dto.response.ProductResponse;
import com.wanted.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
        @Valid @RequestBody ProductCreateRequest productCreateRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productService.create(productCreateRequest));
    }

    @GetMapping
    public ResponseEntity<Object> getAllProduct(
        @RequestParam(required = false, defaultValue = "1") int page,
        @RequestParam(required = false, defaultValue = "10") int perPage,
        @RequestParam(required = false, defaultValue = "created_at:desc") String sort,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) Integer minPrice,
        @RequestParam(required = false) Integer maxPrice,
        @RequestParam(required = false) Integer category,
        @RequestParam(required = false) Integer seller,
        @RequestParam(required = false) Integer brand,
        @RequestParam(required = false) Boolean inStock,
        @RequestParam(required = false) String search
    ) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(
        @PathVariable Long id
    ) {
        return null;
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(
        @PathVariable Long id,
        @Valid @RequestBody ProductCreateRequest productCreateRequest
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteProduct(
        @PathVariable Long id
    ) {
        return null;
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<Object> addProductOptions(
        @PathVariable Long id,
        @Valid @RequestBody ProductOptionRequest createRequest
    ) {
        return null;
    }

    @PutMapping("/{id}/options/{optionId}")
    public ResponseEntity<Object> updateProductOptions(
        @PathVariable Long id,
        @PathVariable Long optionId,
        @Valid @RequestBody ProductOptionRequest updateRequest
    ) {
        return null;
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
