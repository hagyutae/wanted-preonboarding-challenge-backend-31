package com.example.demo.controller;

import com.example.demo.dto.ProductInfoDto;
import com.example.demo.entity.Products;
import com.example.demo.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    //상품 관리 API
    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productService) {
        this.productsService = productService;
    }

    // GET /api/products: 상품 목록 조회 (핕터 조건 적용, 정렬, 페이지네이션)
    @GetMapping
    public ResponseEntity<List<ProductInfoDto>> getAllProducts() {
        List<ProductInfoDto> products = productsService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // GET /api/products/{id}: 상품 상세 조회 (모든 관련 정보 포함)
    @GetMapping("/{id}")
    public ResponseEntity<ProductInfoDto> getProductById(@PathVariable("id") Integer id) {
        ProductInfoDto product = productsService.getProductById(id);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
