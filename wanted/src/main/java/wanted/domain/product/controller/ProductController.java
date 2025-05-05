package wanted.domain.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.common.response.SuccessResponse;
import wanted.domain.product.dto.ProductCreateRequest;
import wanted.domain.product.dto.ProductCreateResponse;
import wanted.domain.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    // 인증 로직 생략
    @PostMapping()
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest){
        ProductCreateResponse productCreateResponse =  productService.createProduct(productCreateRequest);

        return ResponseEntity.ok(SuccessResponse.ok(productCreateResponse));
    }

}
