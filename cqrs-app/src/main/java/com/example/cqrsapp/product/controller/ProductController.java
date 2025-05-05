package com.example.cqrsapp.product.controller;

import com.example.cqrsapp.common.response.APIDataResponse;
import com.example.cqrsapp.product.dto.requset.RegisterProductDto;
import com.example.cqrsapp.product.dto.response.RegisterProductResponseDto;
import com.example.cqrsapp.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    APIDataResponse<RegisterProductResponseDto> createProduct(@RequestBody RegisterProductDto dto) {
        RegisterProductResponseDto result = productService.createProduct(dto);
        return APIDataResponse.success(result,"상품이 성공적으로 등록되었습니다.");
    }

}
