package sample.challengewanted.api.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.api.controller.product.response.ProductResponse;
import sample.challengewanted.api.service.product.ProductService;
import sample.challengewanted.dto.product.PagedResponse;
import sample.challengewanted.dto.product.ProductResponseV1;
import sample.challengewanted.dto.product.ProductSearchCondition;
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
    public ApiResponse<PagedResponse<?>> getProducts(
            @ModelAttribute ProductSearchCondition condition,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<?> page = productService.selectProducts(condition, pageable);
        return ApiResponse.success(PagedResponse.from(page), "상품 목폭을 성공적으로 조회했습니다.");
    }

    @GetMapping("/products/{id}")
    public ApiResponse<ProductResponseV1> getProductById(@PathVariable Long id) {
        ProductResponseV1 product = productService.findProductById(id);
        return ApiResponse.success(product, "상품 상세 정보를 성공적으로 조회했습니다.");
    }
}
