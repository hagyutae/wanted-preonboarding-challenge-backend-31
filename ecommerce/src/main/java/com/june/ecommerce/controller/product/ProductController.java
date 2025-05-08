package com.june.ecommerce.controller.product;

import com.june.ecommerce.dto.product.*;
import com.june.ecommerce.dto.product.create.ProductCreateDto;
import com.june.ecommerce.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품 등록
     * @param requestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateDto requestDto) {
        productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 상품 목록 조회
     * @param condition
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getProducts(ProductSearchCondition condition) {
        List<ProductResponseDto> products = productService.getProducts(condition);
        return ResponseEntity.ok(products);
    }

    /**
     * 상품 상세 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        ProductDetailInfoDto product = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }

    /**
     * 상품 수정
     * @param id
     * @param requestDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id,
                                           @RequestBody ProductCreateDto requestDto) {
        productService.updateProduct(id, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 상품 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }


}
