package com.example.preonboarding.images.controller;

import com.example.preonboarding.images.domain.ProductImages;
import com.example.preonboarding.images.dto.ProductImageDTO;
import com.example.preonboarding.images.service.ProductImageService;
import com.example.preonboarding.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductImageRestController {
    private final ProductImageService productImageService;

    @PostMapping(value = "/products/{id}/images")
    public ResponseEntity<CommonResponse> addProductImages(@PathVariable("id")Long id, @RequestBody ProductImageDTO request){

        ProductImages productImages = productImageService.addProductImage(id, request);

        ProductImageDTO dto = ProductImageDTO.builder()
                .id(productImages.getId())
                .url(productImages.getUrl())
                .altText(productImages.getAltText())
                .isPrimary(productImages.isPrimary())
                .displayOrder(productImages.getDisplayOrder())
                .optionId(productImages.getOptions().getId()).build();


        return ResponseEntity.ok().body(CommonResponse.success(dto, "상품 이미지가 성공적으로 추가되었습니다."));
    }
}
