package com.mkhwang.wantedcqrs.product.controller;

import com.mkhwang.wantedcqrs.config.advice.ApiMessage;
import com.mkhwang.wantedcqrs.product.application.ProductSearchService;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchDetailDto;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchDto;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchResultDto;
import com.mkhwang.wantedcqrs.product.domain.dto.product.ProductCreateRequestDto;
import com.mkhwang.wantedcqrs.product.domain.dto.product.ProductCreateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product", description = "Product API")
@RestController
@RequiredArgsConstructor
public class ProductController {
  private final ProductSearchService productSearchService;

  @ApiMessage("product.create.success")
  @Operation(summary = "상품 등록")
  @PostMapping("/api/products")
  public ProductCreateResponseDto createProduct(@RequestBody @Valid ProductCreateRequestDto productCreateDto) {
    return productSearchService.createProduct(productCreateDto);
  }

  @ApiMessage("product.search.success")
  @Operation(summary = "상품 검색")
  @GetMapping("/api/products")
  public Page<ProductSearchResultDto> searchProducts(@ModelAttribute ProductSearchDto searchDto) {
    return productSearchService.searchProducts(searchDto);
  }

  @ApiMessage("product.detail.success")
  @Operation(summary = "상품 상세 조회")
  @GetMapping("/api/products/{id}")
  public ProductSearchDetailDto getProductDetail(@PathVariable Long id) {
    return productSearchService.getProductDetailById(id);
  }

  @ApiMessage("product.delete.success")
  @Operation(summary = "상품 삭제")
  @DeleteMapping("/api/products/{id}")
  public void deleteProduct(@PathVariable Long id) {
    productSearchService.deleteProduct(id);
  }


}
