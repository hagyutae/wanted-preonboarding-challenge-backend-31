package com.wanted.mono.domain.product.controller;

import com.wanted.mono.domain.product.dto.ProductSearchItem;
import com.wanted.mono.domain.product.dto.model.ProductImageDto;
import com.wanted.mono.domain.product.dto.model.ProductInfoDto;
import com.wanted.mono.domain.product.dto.request.*;
import com.wanted.mono.domain.product.dto.response.ProductOptionSaveResponse;
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
     *
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
     *
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
     *
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<?> detail(@PathVariable Long productId) {
        ProductInfoDto productInfoDto = productService.findById(productId);
        return ResponseEntity.ok()
                .body(CommonResponse.success(productInfoDto, messageUtil.get(PRODUCT_DETAIL_SUCCESS)));
    }

    /**
     * PUT /api/products/{id}
     * 특정 상품 정보를 수정합니다.
     * @param productId
     * @param productRequest
     * @return
     */
    @PutMapping("/{productId}")
    public ResponseEntity<?> update(@PathVariable Long productId, @Valid @RequestBody ProductRequest productRequest) {
        ProductUpdateResponse updateResponse = productService.updateProduct(productId, productRequest);
        return ResponseEntity.ok()
                .body(CommonResponse.success(updateResponse, messageUtil.get(PRODUCT_DETAIL_SUCCESS)));
    }

    /**
     * DELETE /api/products/{id}
     * 특정 상품을 삭제합니다 (소프트 삭제).
     * @param productId
     * @return
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> delete(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok()
                .body(CommonResponse.success(null, messageUtil.get(PRODUCT_DELETE_SUCCESS)));
    }

    /**
     * POST /api/products/{id}/options
     * 특정 상품에 옵션을 추가합니다.
     * @param addOptionRequest
     * @return
     */
    @PostMapping("/{productId}/options")
    public ResponseEntity<?> addOption(@PathVariable Long productId, @Valid @RequestBody ProductAddOptionRequest addOptionRequest) {
        ProductOptionSaveResponse saveResponse = productService.addOption(addOptionRequest, productId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(saveResponse, messageUtil.get(PRODUCT_OPTION_CREATE_SUCCESS)));
    }

    /**
     * DELETE /api/products/{id}/options/{optionId}
     * 특정 상품의 옵션을 삭제합니다.
     * @param productId
     * @param optionId
     * @return
     */
    @DeleteMapping("/{productId}/options/{optionId}")
    public ResponseEntity<?> removeOption(@PathVariable Long productId, @PathVariable Long optionId) {
        productService.deleteOption(productId, optionId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.success(null, messageUtil.get(PRODUCT_OPTION_DELETE_SUCCESS)));
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<?> addImage(@PathVariable Long productId, @Valid @RequestBody ProductImageRequest productImageRequest) {
        ProductImageDto response = productService.addImage(productId, productImageRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.success(response, messageUtil.get(PRODUCT_IMAGE_CREATE_SUCCESS)));
    }
}
