package com.mkhwang.wantedcqrs.product.controller;

import com.mkhwang.wantedcqrs.product.application.ProductOptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ProductOption", description = "Product Option API")
@RestController
@RequiredArgsConstructor
public class ProductOptionController {
  private final ProductOptionService productOptionService;

  @PostMapping("/api/products/{id}/options")
  public Object addProductOption(@PathVariable Long id) {
    // TODO    // Implement add logic here
    return null;
  }

  @PutMapping("/api/products/{id}/options/{optionId}")
  public Object modifyProductOption(@PathVariable Long id, @PathVariable Long optionId) {
    // TODO    // Implement add logic here
    return null;
  }

  @DeleteMapping("/api/products/{id}/options/{optionId}")
  public void deleteProductOption(@PathVariable Long id, @PathVariable Long optionId) {
    // TODO    // Implement add logic here
  }
}
