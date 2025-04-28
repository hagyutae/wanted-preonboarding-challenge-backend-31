package com.mkhwang.wantedcqrs.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductImageController {

  @PostMapping("/api/products/{id}/images")
  public Object addProductImage(@PathVariable Long id) {
    // TODO    // Implement add logic here
    return null;
  }
}
