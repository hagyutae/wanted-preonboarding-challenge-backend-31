package com.pawn.wantedcqrs.product.service;

import com.pawn.wantedcqrs.product.dto.*;
import com.pawn.wantedcqrs.productOptionGroup.dto.ProductOptionGroupDto;
import com.pawn.wantedcqrs.productOptionGroup.service.ProductOptionGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;
    private final ProductOptionGroupService productOptionGroupService;

    @Transactional
    public CreateProductResponse createProduct(CreateProductRequest request) {

        // TODO: Validation Check
        // Brand Validation Check
        // Seller Validation Check
        // Category Validation Check
        // Tag Validation Check

        // Product 등록
        ProductDto savedProductDto = productService.create(request.toProductDto());

        // ProductOptionGroup 저장
        List<ProductOptionGroupDto> productOptionGroups =
                productOptionGroupService.createProductOptionGroupsByProductId(
                        savedProductDto.getId(), request.toOptionGroupDtos()
                );

        // ProductCategory
        List<ProductCategoryDto> productCategoryDtos = request.toProductCategoryDtos(savedProductDto.getId());
        productService.saveCategoriesByProductId(savedProductDto.getId(), productCategoryDtos);

        // ProductTag
        List<ProductTagDto> productTagDtos = request.toProductTagDtos(savedProductDto.getId());
        productService.saveTagsByProductId(savedProductDto.getId(), productTagDtos);


        return CreateProductResponse.builder()
                .id(savedProductDto.getId())
                .name(savedProductDto.getName())
                .slug(savedProductDto.getSlug())
                .createdAt(savedProductDto.getCreatedAt())
                .updatedAt(savedProductDto.getUpdatedAt())
                .build();
    }

}
