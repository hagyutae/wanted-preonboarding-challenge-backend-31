package com.pawn.wantedcqrs.product.controller;

import com.pawn.wantedcqrs.product.dto.CreateProductRequest;
import com.pawn.wantedcqrs.product.dto.CreateProductResponse;
import com.pawn.wantedcqrs.product.dto.ProductDto;
import com.pawn.wantedcqrs.product.service.ProductFacade;
import com.pawn.wantedcqrs.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    private final ProductFacade productFacade;

    @PostMapping()
    public ResponseEntity<CreateProductResponse> create(@RequestBody CreateProductRequest request) {

        CreateProductResponse response = productFacade.createProduct(request);

        return ResponseEntity.ok().body(response);
    }

}
