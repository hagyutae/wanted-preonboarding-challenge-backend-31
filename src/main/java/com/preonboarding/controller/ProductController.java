package com.preonboarding.controller;

import com.preonboarding.domain.*;
import com.preonboarding.dto.request.ProductCreateRequestDto;
import com.preonboarding.dto.response.ProductResponse;
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

    @DeleteMapping
    public ResponseEntity<BaseResponse<ProductResponse>> deleteProduct(@RequestParam Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
