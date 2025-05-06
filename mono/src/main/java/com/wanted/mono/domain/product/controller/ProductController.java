package com.wanted.mono.domain.product.controller;

import com.wanted.mono.domain.product.dto.ProductSearchItem;
import com.wanted.mono.domain.product.dto.model.ProductInfoDto;
import com.wanted.mono.domain.product.dto.request.ProductRequest;
import com.wanted.mono.domain.product.dto.request.ProductSearchRequest;
import com.wanted.mono.domain.product.dto.response.ProductSaveResponse;
import com.wanted.mono.domain.product.dto.response.ProductSearchResponse;
import com.wanted.mono.domain.product.dto.response.ProductUpdateResponse;
import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.product.service.ProductService;
import com.wanted.mono.global.common.CommonResponse;
import com.wanted.mono.global.message.MessageCode;
import com.wanted.mono.global.util.MessageUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wanted.mono.global.message.MessageCode.*;


@RestController
@Slf4j
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final MessageUtil messageUtil;

    /**
     * POST /api/products
     * 새로운 상품을 등록합니다.
     * @param productRequest
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody ProductRequest productRequest) {
        log.info("상품 등록 요청, 이름 : {}", productRequest.getName());
        ProductSaveResponse saveResponse = productService.createProduct(productRequest);
        log.info("상품 등록 완료 : {}", saveResponse.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(saveResponse, messageUtil.get(PRODUCT_CREATE_SUCCESS)));
    }

    /**
     * GET /api/products
     * 상품 목록을 조회합니다.
     * @param productSearchRequest
     * @return
     */
    @GetMapping()
    public ResponseEntity<?> search(@Valid @ModelAttribute ProductSearchRequest productSearchRequest) {
        ProductSearchResponse productSearchResponse = productService.searchProduct(productSearchRequest);

        return ResponseEntity.ok()
                .body(CommonResponse.success(productSearchResponse, messageUtil.get(PRODUCT_SEARCH_SUCCESS)));
    }

    /**
     * GET /api/products/id
     * 상품 상세 정보를 조회합니다.
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<?> detail(@PathVariable Long productId) {
        ProductInfoDto productInfoDto = productService.findById(productId);
        return ResponseEntity.ok()
                .body(CommonResponse.success(productInfoDto, messageUtil.get(PRODUCT_DETAIL_SUCCESS)));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> update(@PathVariable Long productId, @Valid @RequestBody ProductRequest productRequest) {
        ProductUpdateResponse updateResponse = productService.updateProduct(productId, productRequest);
        return ResponseEntity.ok()
                .body(CommonResponse.success(updateResponse, messageUtil.get(PRODUCT_DETAIL_SUCCESS)));
    }

}
