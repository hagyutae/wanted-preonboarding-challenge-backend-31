package com.wanted_preonboarding_challenge_backend.eCommerce.controller;

import com.wanted_preonboarding_challenge_backend.eCommerce.dto.Image.request.ImageAddRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.Image.response.ImageAddResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.request.ProductOptionAddRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.request.ProductOptionUpdateRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.response.ProductOptionAddResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.response.ProductOptionUpdateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.ProductCreateRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.ProductsSearchCondition;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response.ProductCreateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response.ProductDetailResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response.ProductListResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response.ProductUpdateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.common.ApiResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @GetMapping("")
    public ResponseEntity<ApiResponse<ProductListResponse>> getProducts(@ModelAttribute ProductsSearchCondition condition) {
        ProductListResponse response = productService.getProductList(condition);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "상품 목록을 성공적으로 조회했습니다."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProduct(
            @PathVariable Long id
    ){
        ProductDetailResponse response = productService.getProduct(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "상품 상세 정보를 성공적으로 조회했습니다."));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<ProductCreateResponse>> writeProduct(@RequestBody ProductCreateRequest request) {
        ProductCreateResponse response = productService.writeProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "상품이 성공적으로 등록되었습니다."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductUpdateResponse>> updateProduct(@PathVariable Long id, @RequestBody ProductCreateRequest request) {
        ProductUpdateResponse response = productService.updateProduct(id, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(response, "상품이 성공적으로 수정되었습니다."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "상품이 성공적으로 삭제되었습니다."));
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<ApiResponse<ProductOptionAddResponse>> addProductOptions(@PathVariable Long id, @RequestBody ProductOptionAddRequest request) {
        ProductOptionAddResponse response = productService.addProductOption(id, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "상품 옵션이 성공적으로 추가되었습니다"));
    }

    @PutMapping("/{id}/options/{optionId}")
    public ResponseEntity<ApiResponse<ProductOptionUpdateResponse>> updateProductOptions(
            @PathVariable Long id,
            @PathVariable Long optionId,
            @RequestBody ProductOptionUpdateRequest request) {

        ProductOptionUpdateResponse response = productService.updateProductOption(id, optionId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(response, "상품 옵션이 성공적으로 수정되었습니다."));
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<ApiResponse<ImageAddResponse>> addImage(
            @PathVariable Long id,
            @RequestBody ImageAddRequest request) {
        ImageAddResponse response = productService.addImage(id, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "상품 이미지가 성공적으로 추가되었습니다."));
    }
}
