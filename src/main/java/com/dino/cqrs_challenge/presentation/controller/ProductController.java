package com.dino.cqrs_challenge.presentation.controller;

import com.dino.cqrs_challenge.global.response.ApiResponse;
import com.dino.cqrs_challenge.presentation.model.rq.CreateProductImageRequest;
import com.dino.cqrs_challenge.presentation.model.rq.CreateProductOptionRequest;
import com.dino.cqrs_challenge.presentation.model.rq.CreateProductRequest;
import com.dino.cqrs_challenge.presentation.model.rq.UpdateProductOptionRequest;
import com.dino.cqrs_challenge.presentation.model.rq.UpdateProductRequest;
import com.dino.cqrs_challenge.presentation.model.rs.CreateProductImageResponse;
import com.dino.cqrs_challenge.presentation.model.rs.CreateProductOptionResponse;
import com.dino.cqrs_challenge.presentation.model.rs.CreateProductResponse;
import com.dino.cqrs_challenge.presentation.model.rs.UpdateProductOptionResponse;
import com.dino.cqrs_challenge.presentation.model.rs.UpdateProductResponse;
import com.dino.cqrs_challenge.presentation.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품 관리", description = "상품 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "상품 관련 정보를 모두 포함한 상품 등록")
    @PostMapping("/products")
    public ApiResponse<CreateProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        CreateProductResponse responseData = productService.createProduct(createProductRequest);

        return ApiResponse.<CreateProductResponse>builder()
                .success(true)
                .data(responseData)
                .message("상품이 성공적으로 등록되었습니다.")
                .build();
    }

    @Operation(summary = "상품 수정", description = "상품 ID를 기반으로 상품 정보 수정")
    @PutMapping("/products/{id}")
    public ApiResponse<UpdateProductResponse> updateProduct(@PathVariable Long id,
                                                            @RequestBody UpdateProductRequest updateProductRequest) {
        UpdateProductResponse responseData = productService.updateProduct(id, updateProductRequest);

        return ApiResponse.<UpdateProductResponse>builder()
                .success(true)
                .data(responseData)
                .message("상품이 성공적으로 수정되었습니다.")
                .build();
    }

    @Operation(summary = "상품 삭제", description = "특정 상품을 삭제합니다 (소프트 삭제).")
    @DeleteMapping("/products/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("상품이 성공적으로 삭제되었습니다.")
                .build();
    }

    @Operation(summary = "상품 옵션 추가", description = "상품 ID를 기반으로 상품 옵션 추가")
    @PostMapping("/products/{id}/options")
    public ApiResponse<CreateProductOptionResponse> addProductOption(@PathVariable Integer id,
                                                                     @RequestBody CreateProductOptionRequest createProductOptionRequest) {
        CreateProductOptionResponse responseData = productService.addProductOption(id, createProductOptionRequest);

        return ApiResponse.<CreateProductOptionResponse>builder()
                .success(true)
                .data(responseData)
                .message("상품 옵션이 성공적으로 추가되었습니다.")
                .build();
    }

    @Operation(summary = "상품 옵션 수정", description = "상품 ID와 옵션 ID를 기반으로 상품 옵션 수정")
    @PutMapping("/products/{id}/options/{optionId}")
    public ApiResponse<UpdateProductOptionResponse> updateProductOption(@PathVariable Integer id,
                                                                        @PathVariable Integer optionId,
                                                                        @RequestBody UpdateProductOptionRequest updateProductOptionRequest) {
        UpdateProductOptionResponse responseData = productService.updateProductOption(id, optionId, updateProductOptionRequest);

        return ApiResponse.<UpdateProductOptionResponse>builder()
                .success(true)
                .data(responseData)
                .message("상품 옵션이 성공적으로 수정되었습니다.")
                .build();
    }

    @Operation(summary = "상품 옵션 삭제", description = "상품 ID와 옵션 ID를 기반으로 상품 옵션 삭제")
    @DeleteMapping("/products/{id}/options/{optionId}")
    public ApiResponse<Void> deleteProductOption(@PathVariable Integer id, @PathVariable Integer optionId) {
        productService.deleteProductOption(id, optionId);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("상품 옵션이 성공적으로 삭제되었습니다.")
                .build();
    }

    @Operation(summary = "상품 이미지 추가", description = "상품 ID를 기반으로 상품 이미지 추가")
    @PostMapping("/products/{id}/images")
    public ApiResponse<CreateProductImageResponse> addProductImage(@PathVariable Integer id,
                                                                   @RequestBody CreateProductImageRequest productImageRequest) {
        CreateProductImageResponse responseData = productService.addProductImage(id, productImageRequest);

        return ApiResponse.<CreateProductImageResponse>builder()
                .success(true)
                .data(responseData)
                .message("상품 이미지가 성공적으로 추가되었습니다.")
                .build();
    }

}
