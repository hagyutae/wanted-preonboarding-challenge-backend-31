package minseok.cqrschallenge.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.common.dto.ApiResponse;
import minseok.cqrschallenge.common.dto.PaginationResponse;
import minseok.cqrschallenge.product.dto.request.ProductCreateRequest;
import minseok.cqrschallenge.product.dto.request.ProductImageCreateRequest;
import minseok.cqrschallenge.product.dto.request.ProductOptionCreateRequest;
import minseok.cqrschallenge.product.dto.request.ProductUpdateRequest;
import minseok.cqrschallenge.product.dto.response.ProductCreateResponse;
import minseok.cqrschallenge.product.dto.response.ProductDetailResponse;
import minseok.cqrschallenge.product.dto.response.ProductImageResponse;
import minseok.cqrschallenge.product.dto.response.ProductListResponse;
import minseok.cqrschallenge.product.dto.response.ProductOptionResponse;
import minseok.cqrschallenge.product.dto.response.ProductUpdateResponse;
import minseok.cqrschallenge.product.service.ProductImageService;
import minseok.cqrschallenge.product.service.ProductOptionService;
import minseok.cqrschallenge.product.service.ProductService;
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

    private final ProductOptionService productOptionService;

    private final ProductImageService productImageService;


    @PostMapping
    public ResponseEntity<ApiResponse<ProductCreateResponse>> createProduct(
        @Valid @RequestBody ProductCreateRequest request) {
        ProductCreateResponse response = productService.createProduct(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(response, "상품이 성공적으로 등록되었습니다."));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<PaginationResponse<ProductListResponse>>> getProducts(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int perPage,
        @RequestParam(defaultValue = "created_at:desc") String sort,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) Integer minPrice,
        @RequestParam(required = false) Integer maxPrice,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Integer seller,
        @RequestParam(required = false) Integer brand,
        @RequestParam(required = false) Boolean inStock,
        @RequestParam(required = false) String search) {

        PaginationResponse<ProductListResponse> response = productService.getProducts(
            page, perPage, sort, status, minPrice, maxPrice,
            category, seller, brand, inStock, search);

        return ResponseEntity.ok(
            ApiResponse.success(response, "상품 목록을 성공적으로 조회했습니다.")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductDetail(
        @PathVariable Long id) {
        ProductDetailResponse response = productService.getProductDetail(id);
        return ResponseEntity.ok(
            ApiResponse.success(response, "상품 상세 정보를 성공적으로 조회했습니다.")
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductUpdateResponse>> updateProduct(
        @PathVariable Long id,
        @Valid @RequestBody ProductUpdateRequest request) {
        ProductUpdateResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(
            ApiResponse.success(response, "상품이 성공적으로 수정되었습니다.")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(
            ApiResponse.success(null, "상품이 성공적으로 삭제되었습니다.")
        );
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<ApiResponse<ProductOptionResponse>> addProductOption(
        @PathVariable Long id,
        @Valid @RequestBody ProductOptionCreateRequest request) {
        ProductOptionResponse response = productOptionService.addProductOption(id, request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(response, "상품 옵션이 성공적으로 추가되었습니다."));
    }


    @PutMapping("/{id}/options/{optionId}")
    public ResponseEntity<ApiResponse<ProductOptionResponse>> updateProductOption(
        @PathVariable Long id,
        @PathVariable Long optionId,
        @Valid @RequestBody ProductOptionCreateRequest request) {
        ProductOptionResponse response = productOptionService.updateProductOption(id, optionId,
            request);
        return ResponseEntity.ok(
            ApiResponse.success(response, "상품 옵션이 성공적으로 수정되었습니다.")
        );
    }


    @DeleteMapping("/{id}/options/{optionId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductOption(
        @PathVariable Long id,
        @PathVariable Long optionId) {
        productOptionService.deleteProductOption(id, optionId);
        return ResponseEntity.ok(
            ApiResponse.success(null, "상품 옵션이 성공적으로 삭제되었습니다.")
        );
    }


    @PostMapping("/{id}/images")
    public ResponseEntity<ApiResponse<ProductImageResponse>> addProductImage(
        @PathVariable Long id,
        @Valid @RequestBody ProductImageCreateRequest request) {
        ProductImageResponse response = productImageService.addProductImage(id, request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(response, "상품 이미지가 성공적으로 추가되었습니다."));
    }
}