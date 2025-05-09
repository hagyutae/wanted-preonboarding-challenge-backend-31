package com.wanted.mono.domain.category.service;

import com.wanted.mono.domain.category.entity.Category;
import com.wanted.mono.domain.category.entity.ProductCategory;
import com.wanted.mono.domain.category.entity.dto.ProductCategoryDto;
import com.wanted.mono.domain.category.repository.ProductCategoryRepository;
import com.wanted.mono.domain.product.dto.request.ProductCategoryRequest;
import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.global.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductCategoryService {
    private final CategoryService categoryService;
    private final ProductCategoryRepository productCategoryRepository;

    public void createProductCategory(List<ProductCategoryRequest> productCategoryRequests, Product product) {
        log.info("카테고리 Id 리스트화");
        List<Long> categoryIds = productCategoryRequests.stream()
            .map(ProductCategoryRequest::getCategoryId)
            .collect(Collectors.toList());

        log.info("카테고리 벌크 조회 & Map 형태 변환");
        Map<Long, Category> categoryMap = categoryService.findByIds(categoryIds).stream()
            .collect(Collectors.toMap(Category::getId, category -> category));

        log.info("ProductCategory 연관관계 설정");
        List<ProductCategory> productCategories = new ArrayList<>();
        
        for (ProductCategoryRequest request : productCategoryRequests) {
            Category category = categoryMap.get(request.getCategoryId());
            if (category == null) {
                throw new IllegalArgumentException("존재하지 않는 카테고리입니다: " + request.getCategoryId());
            }

            ProductCategory productCategory = ProductCategory.of(request);
            
            productCategory.addProduct(product);
            productCategory.addCategory(category);
            
            productCategories.add(productCategory);
        }

        log.info("ProductCategory 엔티티 리스트 저장");
        productCategoryRepository.saveAll(productCategories);
    }

    public void updateProductCategory(List<ProductCategoryRequest> productCategoryRequests, Product product) {
        log.info("카테고리 Id 리스트화");
        List<Long> categoryIds = productCategoryRequests.stream()
                .map(ProductCategoryRequest::getCategoryId)
                .collect(Collectors.toList());

        log.info("카테고리 벌크 조회 & Map 형태 변환");
        Map<Long, Category> categoryMap = categoryService.findAllByIdsWithProductCategory(categoryIds).stream()
                .collect(Collectors.toMap(Category::getId, category -> category));

        // 새로 생성하여 다시 저장
        List<ProductCategory> newRelations = productCategoryRequests.stream()
                .map(req -> {
                    Category category = categoryMap.get(req.getCategoryId());
                    ProductCategory productCategory = ProductCategory.of(new ProductCategoryRequest(category.getId(), req.getIsPrimary()));
                    productCategory.addProduct(product);
                    productCategory.addCategory(category);
                    return productCategory;
                })
                .toList();

        productCategoryRepository.saveAll(newRelations);
    }

    public ProductCategoryDto findById(Long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        return ProductCategoryDto.of(productCategory);
    }
}
