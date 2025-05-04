package com.ecommerce.products.application.service;

import com.ecommerce.products.application.dto.MainPageDto;
import com.ecommerce.products.application.mapper.ProductMapper;
import com.ecommerce.products.infra.jpa.entity.Category;
import com.ecommerce.products.infra.jpa.entity.Product;
import com.ecommerce.products.infra.jpa.entity.ProductStatus;
import com.ecommerce.products.infra.jpa.repository.CategoryRepository;
import com.ecommerce.products.infra.jpa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public MainPageDto.MainPage getMainPageContents() {
        // 1. 신규 상품 조회 (최근 등록순 10개)
        List<Product> newProducts = productRepository.findTop5ByStatusOrderByCreatedAtDesc(ProductStatus.ACTIVE);

        // 2. 인기 상품 조회 (리뷰 평점 높은순 10개)
        List<Product> popularProducts = productRepository.findTop5PopularProducts();


        // 3. 주요 카테고리 조회 (1단계 카테고리 중 상품이 많은 순으로 5개)
        List<Category> categories = categoryRepository.findByLevel(1);

        // 카테고리별 상품 수 카운트
        List<Object[]> categoryCountResults = productRepository.countProductsByCategories();
        Map<Long, Long> categoryProductCounts = new HashMap<>();

        for (Object[] result : categoryCountResults) {
            Long categoryId = (Long) result[0];
            Long productCount = ((Number) result[1]).longValue();
            categoryProductCounts.put(categoryId, productCount);
        }

        List<MainPageDto.FeaturedCategory> featuredCategories = categories.stream()
                .map(category -> {
                    long productCount = categoryProductCounts.getOrDefault(category.getId(), 0L);
                    return MainPageDto.FeaturedCategory.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .slug(category.getSlug())
                            .imageUrl(category.getImageUrl())
                            .productCount((int) productCount)
                            .build();
                })
                .filter(c -> c.getProductCount() > 0) // 상품이 있는 카테고리만 필터링
                .sorted((c1, c2) -> c2.getProductCount() - c1.getProductCount())
                .limit(5)
                .collect(Collectors.toList());

        // 결과 DTO 생성 및 반환
        return MainPageDto.MainPage.builder()
                .newProducts(newProducts.stream().map(productMapper::toProductSummaryDto).toList())
                .popularProducts(popularProducts.stream().map(productMapper::toProductSummaryDto).toList())
                .featuredCategories(featuredCategories)
                .build();
    }
}