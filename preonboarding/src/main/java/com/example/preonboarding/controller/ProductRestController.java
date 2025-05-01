package com.example.preonboarding.controller;

import com.example.preonboarding.domain.Products;
import com.example.preonboarding.exception.BadRequestException;
import com.example.preonboarding.request.ProductSearchRequest;
import com.example.preonboarding.dto.ProductsDTO;
import com.example.preonboarding.request.ProductsRequest;
import com.example.preonboarding.response.CommonResponse;
import com.example.preonboarding.response.error.ErrorCode;
import com.example.preonboarding.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @PostMapping(value = "/products")
    public CommonResponse addProducts(@Validated @RequestBody ProductsRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, Object> details = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    details.put(toSnakeCase(error.getField()), error.getDefaultMessage())
            );
            throw new BadRequestException(ErrorCode.INVALID_INPUT, details);
        }
        Products products = productService.addProducts(request);
        ProductsDTO dto = new ProductsDTO(products);
        return CommonResponse.success(dto);
    }
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

    private String toSnakeCase(String fieldPath) {
        String[] parts = fieldPath.split("\\.");
        return Arrays.stream(parts)
                .map(this::camelToSnake)
                .reduce((a, b) -> b)
                .orElse(fieldPath);
    }

    private String camelToSnake(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
