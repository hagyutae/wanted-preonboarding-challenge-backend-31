package com.mkhwang.wantedcqrs.product.controller;

import com.mkhwang.wantedcqrs.product.application.ProductSearchService;
import com.mkhwang.wantedcqrs.product.domain.Product;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product", description = "Product API")
@RestController
@RequiredArgsConstructor
public class ProductController {
  private final ProductSearchService productSearchService;

  @Operation(summary = "상품 검색")
  @GetMapping("/api/products")
  public Page<Product> searchProducts(@ModelAttribute ProductSearchDto searchDto) {
    return productSearchService.searchProducts(searchDto);
  }
}
