package com.ecommerce.product.presentation;

import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.common.response.PageResponse;
import com.ecommerce.product.application.ProductService;
import com.ecommerce.product.application.dto.req.ProductCreateRequest;
import com.ecommerce.product.application.dto.req.ProductSearchRequest;
import com.ecommerce.product.application.dto.res.ProductCreatedResponse;
import com.ecommerce.product.application.dto.res.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductCreatedResponse>> createProduct(@RequestBody ProductCreateRequest request) {
        ProductCreatedResponse response = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "상품이 성공적으로 등록되었습니다."));
    }

    /**
     * 상품 목록을 조회합니다.
     *
     * @param request 검색 조건 (페이징, 필터링, 정렬)
     * @return 상품 목록 응답
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getProducts(@Valid ProductSearchRequest request) {
        Page<ProductResponse> products = productService.findProducts(request);
        PageResponse<ProductResponse> pageResponse = PageResponse.of(products);

        return ResponseEntity.ok(ApiResponse.success(pageResponse, "상품 목록을 성공적으로 조회했습니다."));
    }
}
