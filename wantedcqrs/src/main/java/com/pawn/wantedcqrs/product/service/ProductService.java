package com.pawn.wantedcqrs.product.service;

import com.pawn.wantedcqrs.common.exception.e4xx.ConflictException;
import com.pawn.wantedcqrs.product.dto.*;
import com.pawn.wantedcqrs.product.entity.Product;
import com.pawn.wantedcqrs.product.entity.ProductCategory;
import com.pawn.wantedcqrs.product.entity.ProductTag;
import com.pawn.wantedcqrs.product.repository.ProductRepository;
import com.pawn.wantedcqrs.productOptionGroup.repository.ProductCategoryRepository;
import com.pawn.wantedcqrs.productOptionGroup.repository.ProductTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    private final ProductTagRepository productTagRepository;

    @Transactional
    public ProductDto create(ProductDto productParam) {

        // Product 저장
        checkDupSlug(productParam.getSlug());

        ProductDetailDto detailParam = productParam.getDetail();
        ProductPriceDto priceParam = productParam.getPrice();

        Product newProduct = productParam.toEntity(
                detailParam,
                priceParam
        );

        Product savedProduct = productRepository.save(newProduct);

        return ProductDto.fromEntity(savedProduct);
    }

    private void checkDupSlug(String slug) {
        boolean isDupProduct = productRepository.findBySlug(slug).isPresent();
        if (isDupProduct) {
            throw ConflictException.PRODUCT_SLUG_DUP.getResponseException();
        }
    }

    @Transactional
    public void saveCategoriesByProductId(Long productId, List<ProductCategoryDto> productCategoryDtos) {
//        List<ProductCategory> productCategories = productCategoryDtos.stream().map(ProductCategoryDto::toEntity).toList();
        List<ProductCategory> productCategories = productCategoryDtos.stream()
                .map(dto -> ProductCategory.builder()
                        .productId(productId) // 전달된 productId 사용
                        .categoryId(dto.getCategoryId())
                        .isPrimary(dto.isPrimary())
                        .build())
                .toList();

        productCategoryRepository.saveAll(productCategories);
    }

    @Transactional
    public void saveTagsByProductId(Long productId, List<ProductTagDto> productTagDtos) {
//        List<ProductTag> productTags = productTagDtos.stream().map(ProductTagDto::toEntity).toList();
        List<ProductTag> productTags = productTagDtos.stream()
                .map(dto -> ProductTag.builder()
                        .productId(productId) // 전달된 productId 사용
                        .tagId(dto.getTagId())
                        .build())
                .toList();

        productTagRepository.saveAll(productTags);
    }

}
