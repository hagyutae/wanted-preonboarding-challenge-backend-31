package com.challenge.onboarding.product.web.api;

import com.challenge.onboarding.product.service.ProductService;
import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import com.challenge.onboarding.product.service.dto.request.ProductSearchCondition;
import com.challenge.onboarding.product.service.dto.response.CreateProductResponse;
import com.challenge.onboarding.product.service.dto.response.ProductListResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ProductListResponse getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(defaultValue = "created_at:desc") String sort,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer[] category,
            @RequestParam(required = false) Integer seller,
            @RequestParam(required = false) Integer brand,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) String search
    ) {
        return productService.getProductList(
                new ProductSearchCondition(
                        page,
                        perPage,
                        sort,
                        status,
                        minPrice,
                        maxPrice,
                        category != null ? List.of(category) : List.of(),
                        seller,
                        brand,
                        inStock,
                        search
                ));
    }

    @PostMapping
    public CreateProductResponse createProduct(
            @RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }
}
