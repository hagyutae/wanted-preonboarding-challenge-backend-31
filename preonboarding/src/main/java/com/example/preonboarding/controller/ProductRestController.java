package com.example.preonboarding.controller;

import com.example.preonboarding.dto.ProductSearchRequest;
import com.example.preonboarding.response.CommonResponse;
import com.example.preonboarding.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;
    @GetMapping(value = "/products")
    public CommonResponse findAllProducts(ProductSearchRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("items", productService.findAllProducts(request));
        return CommonResponse.success(map);
    }

    @GetMapping(value = "/products/{id}")
    public CommonResponse findProductsById(@PathVariable("id")Long id) {
        return CommonResponse.success(productService.findProductsById(id));
    }
}
