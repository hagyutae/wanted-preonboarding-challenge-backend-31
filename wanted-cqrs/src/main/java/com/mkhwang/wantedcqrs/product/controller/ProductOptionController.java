package com.mkhwang.wantedcqrs.product.controller;

import com.mkhwang.wantedcqrs.product.application.ProductOptionService;
import com.mkhwang.wantedcqrs.product.domain.dto.option.ProductOptionCreateRequestDto;
import com.mkhwang.wantedcqrs.product.domain.dto.option.ProductOptionCreateResponseDto;
import com.mkhwang.wantedcqrs.product.domain.dto.option.ProductOptionModifyRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ProductOption", description = "Product Option API")
@RestController
@RequiredArgsConstructor
public class ProductOptionController {
  private final ProductOptionService productOptionService;

  @PostMapping("/api/products/{id}/options")
  public ProductOptionCreateResponseDto addProductOption(@PathVariable Long id,
                                                         @RequestBody @Valid ProductOptionCreateRequestDto dto) {
    return productOptionService.addProductOption(id, dto);
  }

  @PutMapping("/api/products/{id}/options/{optionId}")
  public Object modifyProductOption(@PathVariable Long id, @PathVariable Long optionId,
                                    @RequestBody @Valid ProductOptionModifyRequestDto dto) {
    return productOptionService.modifyProductOption(id, optionId, dto);
  }

  @DeleteMapping("/api/products/{id}/options/{optionId}")
  public void deleteProductOption(@PathVariable Long id, @PathVariable Long optionId) {
    productOptionService.deleteProductOption(id, optionId);
  }
}
