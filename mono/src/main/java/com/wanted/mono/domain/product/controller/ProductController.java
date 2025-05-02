package com.wanted.mono.domain.product.controller;

import com.wanted.mono.domain.product.dto.ProductRequest;
import com.wanted.mono.domain.product.dto.response.ProductSaveResponse;
import com.wanted.mono.domain.product.service.ProductService;
import com.wanted.mono.global.common.CommonResponse;
import com.wanted.mono.global.message.MessageCode;
import com.wanted.mono.global.util.MessageUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final MessageUtil messageUtil;
    /**
     * POST /api/products: 상품 등록 (관련 정보 모두 포함)
     *
     */
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody ProductRequest productRequest) {
        log.info("상품 등록 요청, 이름 : {}", productRequest.getName());
        ProductSaveResponse saveResponse = productService.createProduct(productRequest);
        log.info("상품 등록 완료 : {}", saveResponse.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(saveResponse, messageUtil.get(MessageCode.PRODUCT_CREATE_SUCCESS)));
    }

}
