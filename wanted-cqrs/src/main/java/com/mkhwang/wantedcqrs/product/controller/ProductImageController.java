package com.mkhwang.wantedcqrs.product.controller;

import com.mkhwang.wantedcqrs.config.advice.ApiMessage;
import com.mkhwang.wantedcqrs.product.application.ProductImageService;
import com.mkhwang.wantedcqrs.product.domain.dto.product.ProductImageCreateDto;
import com.mkhwang.wantedcqrs.product.domain.dto.product.ProductImageCreateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductImageController {
  private final ProductImageService productImageService;

  @ApiMessage("product.image.create.success")
  @Operation(summary = "상품 이미지 등록")
  @PostMapping("/api/products/{id}/images")
  public ProductImageCreateResponseDto addProductImage(@PathVariable Long id,
                                                       @RequestBody @Valid ProductImageCreateDto dto) {
    return productImageService.addProductImage(id, dto);
  }
}
