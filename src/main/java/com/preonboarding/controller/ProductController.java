package com.preonboarding.controller;

import com.preonboarding.domain.*;
import com.preonboarding.dto.request.product.*;
import com.preonboarding.dto.response.product.ProductImageResponse;
import com.preonboarding.dto.response.product.ProductOptionResponse;
import com.preonboarding.dto.response.product.ProductResponse;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.service.brand.BrandService;
import com.preonboarding.service.category.CategoryService;
import com.preonboarding.service.product.ProductService;
import com.preonboarding.service.seller.SellerService;
import com.preonboarding.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SellerService sellerService;
    private final BrandService brandService;
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<BaseResponse<ProductResponse>> addProduct(@RequestBody ProductCreateRequestDto dto) {
        Seller seller = sellerService.getSeller(dto.getSellerId());
        Brand brand = brandService.getBrand(dto.getBrandId());
        List<ProductCategory> productCategoryList = categoryService.createProductCategories(dto.getCategories());
        List<ProductTag> productTagList = tagService.createProductTag(dto.getTags());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(dto,productCategoryList,productTagList,seller,brand));
    }

    @PostMapping("/{id}/options")
    public ResponseEntity<BaseResponse<ProductOptionResponse>> addOption(@PathVariable("id") Long id, @RequestBody ProductOptionAddRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.addProductOption(id,dto));
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<BaseResponse<ProductImageResponse>> addProductImage(@PathVariable("id") Long id, @RequestBody ProductImageRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.addProductImage(id,dto));
    }

    @PutMapping
    public ResponseEntity<BaseResponse<ProductResponse>> editProduct(@RequestParam Long id,@RequestBody ProductEditRequestDto dto) {
        Seller seller = sellerService.getSeller(dto.getSellerId());
        Brand brand = brandService.getBrand(dto.getBrandId());
        List<ProductCategory> productCategoryList = categoryService.createProductCategories(dto.getCategories());
        List<ProductTag> productTagList = tagService.createProductTag(dto.getTags());

        return ResponseEntity.ok(productService.editProduct(id,dto,productCategoryList,productTagList,seller,brand));
    }

    @PutMapping("/{id}/options/{optionId}")
    public ResponseEntity<BaseResponse<ProductOptionResponse>> editOption(@PathVariable("id") Long id, @PathVariable("optionId") Long optionId, @RequestBody ProductOptionRequestDto dto) {
        return ResponseEntity.ok(productService.editProductOption(id,optionId,dto));
    }

    @DeleteMapping("/{id}/options/{optionId}")
    public ResponseEntity<BaseResponse<ProductOptionResponse>> deleteOption(@PathVariable("id") Long id, @PathVariable("optionId") Long optionId) {
        return ResponseEntity.ok(productService.deleteProductOption(id,optionId));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<ProductResponse>> deleteProduct(@RequestParam Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
