package com.example.demo.product.controller;

import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.response.PageResponse;
import com.example.demo.common.util.PageableCreator;
import com.example.demo.product.controller.request.AddProductImageRequest;
import com.example.demo.product.controller.request.CreateProductRequest;
import com.example.demo.product.controller.request.UpdateProductRequest;
import com.example.demo.product.dto.*;
import com.example.demo.product.entity.ProductStatus;
import com.example.demo.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // TODO : ACCESS TOKEN
    @PostMapping
    public ResponseEntity<ApiResponse<CreateProductResult>> createProduct(
            @Valid @RequestBody CreateProductRequest createProductRequest
    ) {
        CreateProductResult createProductResult = productService.createProduct(createProductRequest);
        return new ResponseEntity<>(
                ApiResponse.success(createProductResult, "상품이 성공적으로 등록되었습니다."),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductSummary>>> findProductSummaryPage(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "created_at:desc") List<String> sort,
            @RequestParam(name = "status", required = false) ProductStatus status,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
            @RequestParam(name = "category", required = false) List<Long> category,
            @RequestParam(name = "seller", required = false) Long seller,
            @RequestParam(name = "brand", required = false) Long brand,
            @RequestParam(name = "inStock", required = false) Boolean inStock,
            @RequestParam(name = "search", required = false) String search
    ) {
        ProductQueryFilter productQueryFilter = ProductQueryFilter.builder()
                .status(status)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .categoryIds(category)
                .seller(seller)
                .brand(brand)
                .inStock(inStock)
                .search(search)
                .build();
        Pageable pageable = PageableCreator.create(page, perPage, sort);

        Page<ProductSummary> productSummaryPage = productService.findProductSummaryPage(productQueryFilter, pageable);

        return new ResponseEntity<>(
                ApiResponse.success(PageResponse.of(productSummaryPage), "상품 목록을 성공적으로 조회했습니다."),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetail>> findById(
            @Positive @PathVariable("id") Long id
    ) {
        ProductDetail productDetail = productService.findProductDetail(id);

        return new ResponseEntity<>(
                ApiResponse.success(productDetail, "상품 상세 정보를 성공적으로 조회했습니다."),
                HttpStatus.OK
        );
    }

    // TODO : ACCESS TOKEN
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateProductResult>> updateProduct(
            @Positive @PathVariable("id") Long id,
            @Valid @RequestBody UpdateProductRequest updateProductRequest
    ) {
        UpdateProductResult updateProductResult = productService.updateProduct(id, updateProductRequest);
        return new ResponseEntity<>(
                ApiResponse.success(updateProductResult, "상품이 성공적으로 수정되었습니다."),
                HttpStatus.OK);
    }

    // TODO : ACCESS TOKEN
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@Positive @PathVariable("id") Long id) {
        productService.deleteById(id);
        return new ResponseEntity<>(
                ApiResponse.success(null, "상품이 성공적으로 삭제되었습니다."),
                HttpStatus.OK
        );
    }

    // TODO : ACCESS TOKEN
    @PostMapping("/{id}/images")
    public ResponseEntity<ApiResponse<AddProductImageResult>> addProductImage(@Positive @PathVariable("id") Long id,
                                                                                  @Valid @RequestBody AddProductImageRequest addProductImageRequest) {
        AddProductImageResult addProductImageResult = productService.addProductImage(id, addProductImageRequest);

        return new ResponseEntity<>(
                ApiResponse.success(addProductImageResult, "상품 이미지가 성공적으로 추가되었습니다."),
                HttpStatus.CREATED
        );
    }
}
