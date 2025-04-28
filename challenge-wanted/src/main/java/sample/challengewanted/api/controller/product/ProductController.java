package sample.challengewanted.api.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.api.controller.product.response.ProductResponse;
import sample.challengewanted.api.service.product.ProductService;
import sample.challengewanted.dto.ProductSearchCondition;
import sample.challengewanted.dto.common.ApiResponse;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/insert")
    public ApiResponse<?> insert(@RequestBody ProductCreateRequest request) {
        ProductResponse product = productService.createProduct(request);
        return ApiResponse.success(product);
    }

    @GetMapping("/products")
    public ApiResponse<Page> getProducts(ProductSearchCondition condition, Pageable pageable) {
        Page page = productService.selectProducts(condition, pageable);
        return ApiResponse.success(page);
    }
}
