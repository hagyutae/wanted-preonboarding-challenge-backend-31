package com.example.preonboarding.controller;

import com.example.preonboarding.domain.ProductOption;
import com.example.preonboarding.domain.Products;
import com.example.preonboarding.dto.OptionDTO;
import com.example.preonboarding.exception.BadRequestException;
import com.example.preonboarding.request.ProductOptionRequest;
import com.example.preonboarding.request.ProductSearchRequest;
import com.example.preonboarding.dto.ProductsDTO;
import com.example.preonboarding.request.ProductsRequest;
import com.example.preonboarding.response.CommonResponse;
import com.example.preonboarding.response.error.ErrorCode;
import com.example.preonboarding.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommonResponse> addProducts(@Validated @RequestBody ProductsRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, Object> details = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    details.put(toSnakeCase(error.getField()), error.getDefaultMessage())
            );
            throw new BadRequestException(ErrorCode.INVALID_INPUT, details);
        }
        Products products = productService.addProducts(request);
        ProductsDTO dto = new ProductsDTO(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(dto, "상품이 성공적으로 등록되었습니다."));
    }
    @GetMapping(value = "/products")
    public ResponseEntity<CommonResponse> findAllProducts(ProductSearchRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("items", productService.findAllProducts(request));
        return ResponseEntity.ok().body(CommonResponse.success(map, "상품이 성공적으로 등록되었습니다."));
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<CommonResponse> findProductsById(@PathVariable("id")Long id) {
        return ResponseEntity.ok().body(CommonResponse.success(productService.findProductsById(id), "상품 상세 정보를 성공적으로 조회했습니다."));
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<CommonResponse> updateProducts(@PathVariable("id")Long id,@Validated @RequestBody ProductsRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> details = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    details.put(toSnakeCase(error.getField()), error.getDefaultMessage())
            );
            throw new BadRequestException(ErrorCode.INVALID_INPUT, details);
        }

        Products products = productService.updateProducts(request,id);
        ProductsDTO dto = new ProductsDTO(products);
        return ResponseEntity.ok().body(CommonResponse.success(dto, "상품이 성공적으로 수정되었습니다."));
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<CommonResponse> deleteProducts(@PathVariable("id")Long id) {
        productService.deleteProducts(id);
        return ResponseEntity.ok().body(CommonResponse.success(null, "상품이 성공적으로 삭제되었습니다."));
    }

    @PostMapping(value = "/products/{id}/options")
    public ResponseEntity<CommonResponse> addProductOptions(@PathVariable("id") Long id, @RequestBody ProductOptionRequest request) {
        OptionDTO dto = new OptionDTO(productService.addProductOptions(id, request));
        return ResponseEntity.ok().body(CommonResponse.success(dto, "상품 옵션이 성공적으로 추가 되었습니다."));
    }

    @PutMapping(value = "/products/{id}/options/{optionId}")
    public ResponseEntity<CommonResponse> updateProductOptions(@PathVariable("id")Long id,@PathVariable("optionId")Long optionId,@RequestBody ProductOptionRequest request){
        OptionDTO dto = new OptionDTO(productService.updateProductOptions(id, optionId, request));
        return ResponseEntity.ok().body(CommonResponse.success(dto, "상품 옵션이 성공적으로 수정되었습니다."));
    }

    @DeleteMapping(value = "/products/{id}/options/{optionId}")
    public ResponseEntity<CommonResponse> deleteProductOptions(@PathVariable("id")Long id,@PathVariable("optionId")Long optionId,@RequestBody ProductOptionRequest request){
        productService.deleteProductOptions(id, optionId, request);
        return ResponseEntity.ok().body(CommonResponse.success(null, "상품 옵션이 성공적으로 삭제되었습니다."));
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
