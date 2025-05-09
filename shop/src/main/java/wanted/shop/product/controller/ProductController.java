package wanted.shop.product.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.shop.common.api.Message;
import wanted.shop.common.api.SuccessResponse;
import wanted.shop.product.dto.ProductCreateRequest;
import wanted.shop.product.dto.ProductCreateResponse;
import wanted.shop.product.service.ProductService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public SuccessResponse<ProductCreateResponse> createProduct(@RequestBody ProductCreateRequest request) {
        ProductCreateResponse response = productService.createProduct(request);
        return new SuccessResponse<>(response, new Message("상품이 성공적으로 등록되었습니다."));
    }
}
