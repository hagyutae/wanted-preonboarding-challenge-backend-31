package com.ecommerce.products.controller;

import com.ecommerce.products.application.dto.ProductDto;
import com.ecommerce.products.application.service.ProductService;
import com.ecommerce.products.controller.dto.*;
import com.ecommerce.products.controller.mapper.ProductControllerMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductControllerMapper mapper;

    public ProductController(
            ProductService productService,
            ProductControllerMapper mapper
    ) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<APIResponse<ProductDto.Product>> createProduct(
            @RequestBody ProductCreateRequest request
    ) {
        ProductDto.CreateRequest createRequest = mapper.toProductDtoCreateRequest(request);
        ProductDto.Product createdProduct = productService.createProduct(createRequest);
        return APIResponse.success(createdProduct, "상품이 성공적으로 등록되었습니다.").build(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProductDto.Product>> getProduct(@PathVariable Long id) {
        ProductDto.Product foundProduct = productService.getProductById(id);
        return APIResponse.success(foundProduct, "상품 상세 정보를 성공적으로 조회했습니다.").build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ProductDto.Product>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateRequest request
    ) {
        ProductDto.UpdateRequest updateRequest = mapper.toServiceUpdateDto(request);
        ProductDto.Product updatedProduct = productService.updateProduct(id, updateRequest);
        return APIResponse.success(updatedProduct, "상품이 성공적으로 수정되었습니다.").build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(APIResponse.success(null, "상품이 성공적으로 삭제되었습니다."));
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<APIResponse<ProductDto.Option>> addProductOption(
            @PathVariable Long id,
            @RequestParam Long optionGroupId,
            @RequestBody ProductOptionRequest request
    ) {
        ProductDto.Option createRequest = mapper.toProductDtoOptionWithOptionGroupId(optionGroupId, request);
        ProductDto.Option createdOption = productService.addProductOption(id, createRequest);
        return APIResponse.success(createdOption, "상품 옵션이 성공적으로 추가되었습니다.").build(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/options/{optionId}")
    public ResponseEntity<APIResponse<ProductDto.Option>> updateProductOption(
            @PathVariable Long id,
            @PathVariable Long optionId,
            @RequestBody ProductOptionRequest request
    ) {
        ProductDto.Option updatedRequest = mapper.toProductDtoOptionOptionId(optionId, request);
        ProductDto.Option updatedOption = productService.updateProductOption(id, updatedRequest);
        return APIResponse.success(updatedOption, "상품 옵션이 성공적으로 수정되었습니다.").build();
    }

    @DeleteMapping("/{id}/options/{optionId}")
    public ResponseEntity<APIResponse<Void>> deleteProductOption(
            @PathVariable Long id,
            @PathVariable Long optionId
    ) {
        productService.deleteProductOption(id, optionId);
        return ResponseEntity.ok(APIResponse.success(null, "상품 옵션이 성공적으로 삭제되었습니다."));
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<APIResponse<ProductDto.Image>> addProductImage(
            @PathVariable Long id,
            @RequestBody ProductImageRequest request
    ) {
        ProductDto.Image createRequest = mapper.toProductDtoImage(request);
        ProductDto.Image createdImage = productService.addProductImage(id, createRequest);
        return APIResponse.success(createdImage, "상품 이미지가 성공적으로 추가되었습니다.").build(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<ProductListResponse>> getProducts(@ParameterObject ProductListRequest request) {
        return APIResponse.success(
                productService.getProducts(mapper.toProductDtoListRequest(request)),
                "상품 목록을 성공적으로 조회했습니다."
        ).build();
    }
}
