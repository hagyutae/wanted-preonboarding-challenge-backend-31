package com.sandro.wanted_shop.product.web;

import com.sandro.wanted_shop.product.ProductService;
import com.sandro.wanted_shop.product.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: input 검증하기
@RequiredArgsConstructor
@RequestMapping("/api/products")
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public void registerProduct(@Validated @RequestBody CreateProductCommand command) {
        productService.register(command);
    }

    /*
    - 검색 기능: 상품명, 상품 전체 설명, 브랜드, 태그, 카테고리
    - 필터: 태그, 등록일, 판매자, 브랜드, 가격 범위, 카테고리, 재고 유무
    - 정렬: 가격, 평점, 최신순
    - 페이지네이션
     */
    @GetMapping
    public Page<ProductListDto> getAllProducts(@PageableDefault Pageable pageable,
                                               @ModelAttribute ProductFilterDto filter) {
        return productService.getAllProducts(pageable, filter);
    }

    @GetMapping("/{id}")
    public ProductDto getProductDetail(@PathVariable Long id) {
        return productService.getProductDetail(id);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody UpdateProductCommand command) {
        productService.updateProduct(id, command);
    }

    // TODO: 삭제 쿼리 최적화하기
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PostMapping("/{id}/options")
    public void addOption(@PathVariable Long id, @RequestBody CreateOptionCommand command) {
        productService.addOption(id, command);
    }

    @PutMapping("/{id}/options/{optionId}")
    public void updateOption(@PathVariable Long id,
                             @PathVariable Long optionId,
                             @RequestBody UpdateOptionCommand command) {
        productService.updateOption(id, optionId, command);
    }

    @DeleteMapping("/{id}/options/{optionId}")
    public void deleteOption(@PathVariable Long id, @PathVariable Long optionId) {
        productService.deleteOption(id, optionId);
    }

    @PostMapping("/{id}/images")
    public void addImage(@PathVariable Long id, @RequestBody List<CreateImageCommand> commands) {
        productService.addImage(id, commands);
    }
}
