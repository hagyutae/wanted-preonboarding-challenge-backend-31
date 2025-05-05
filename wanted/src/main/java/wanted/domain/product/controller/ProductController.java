package wanted.domain.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import wanted.common.response.SuccessResponse;
import wanted.domain.product.dto.request.ProductOptionRequest;
import wanted.domain.product.dto.request.ProductRequest;
import wanted.domain.product.dto.response.ProductCreateResponse;
import wanted.domain.product.dto.ProductSearchCondition;
import wanted.domain.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    // 인증 로직 생략
    @PostMapping()
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest){
        ProductCreateResponse productCreateResponse =  productService.createProduct(productRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.ok(productCreateResponse));
    }

    @GetMapping()
    public ResponseEntity<?> getProducts(@ModelAttribute ProductSearchCondition productSearchCondition) {
        return ResponseEntity.ok(SuccessResponse.ok(productService.getFilteredProducts(productSearchCondition)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable(value = "id") Long productId) {
        return ResponseEntity.ok(SuccessResponse.ok(productService.getProduct(productId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(value = "id") Long productId, @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(SuccessResponse.ok(productService.updateProduct(productId, productRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(SuccessResponse.ok(null));
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<?> createProductOption(@PathVariable(value = "id") Long productId, @Valid @RequestBody ProductOptionRequest productOptionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.ok(productService.createProductOption(productId, productOptionRequest)));
    }

    @DeleteMapping("/{id}/options/{optionId}")
    public ResponseEntity<?> deleteProductOption(@PathVariable(value = "id") Long productId, @PathVariable(value = "optionId") Long optionId) {
        productService.deleteProductOption(productId, optionId);
        return ResponseEntity.ok(SuccessResponse.ok(null));
    }
}
