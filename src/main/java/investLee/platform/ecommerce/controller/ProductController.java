package investLee.platform.ecommerce.controller;

import investLee.platform.ecommerce.dto.request.*;
import investLee.platform.ecommerce.dto.response.ProductDetailResponse;
import investLee.platform.ecommerce.dto.response.ProductSummaryResponse;
import investLee.platform.ecommerce.dto.response.ReviewResponse;
import investLee.platform.ecommerce.service.ProductService;
import investLee.platform.ecommerce.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody @Valid ProductCreateRequest requestDTO) {
        Long id = productService.createProduct(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping
    public ResponseEntity<Page<ProductSummaryResponse>> searchProducts(ProductSearchConditionRequest conditionDTO) {
        Page<ProductSummaryResponse> result = productService.searchProducts(conditionDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable Long id) {
        ProductDetailResponse product = productService.getProductDetail(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id,
                                              @RequestBody @Valid ProductUpdateRequest dto) {
        productService.updateProduct(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateProductStatus(@PathVariable Long id,
                                                    @RequestBody @Valid ProductStatusUpdateRequest dto) {
        productService.updateProductStatus(id, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/related")
    public ResponseEntity<List<ProductSummaryResponse>> getRelatedProducts(@PathVariable Long id) {
        List<ProductSummaryResponse> related = productService.getRelatedProducts(id);
        return ResponseEntity.ok(related);
    }

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<Long> createReview(@PathVariable Long productId,
                                             @RequestBody @Valid ReviewCreateRequest dto) {
        Long reviewId = reviewService.createReview(productId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewId);
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<Page<ReviewResponse>> getReviews(@PathVariable Long productId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getReviews(productId, page, size));
    }
}