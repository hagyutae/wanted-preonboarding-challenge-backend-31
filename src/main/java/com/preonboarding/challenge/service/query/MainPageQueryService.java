package com.preonboarding.challenge.service.query;

import com.preonboarding.challenge.service.dto.MainPageDto;
import com.preonboarding.challenge.service.query.entity.ProductDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageQueryService implements MainPageQueryHandler {

    private final ProductDocumentOperations productDocumentOperations;
    private final ProductDocumentMapper productDocumentMapper;

    @Override
    @Cacheable(value = "mainPage")
    public MainPageDto.MainPage getMainPageContents() {
        // 1. 신규 상품 조회 (최근 등록순 5개)
        List<ProductDocument> newProducts = productDocumentOperations.findNewProducts(5);

        // 2. 인기 상품 조회 (리뷰 평점 높은순 5개)
        List<ProductDocument> popularProducts = productDocumentOperations.findPopularProducts(5);

        // 3. 주요 카테고리 조회 (1단계 카테고리 중 상품이 많은 순으로 5개)
        List<MainPageDto.FeaturedCategory> featuredCategories = productDocumentOperations.findFeaturedCategories(5);

        // 결과 DTO 생성 및 반환
        return MainPageDto.MainPage.builder()
                .newProducts(newProducts.stream()
                        .map(productDocumentMapper::toProductSummaryDto)
                        .collect(Collectors.toList()))
                .popularProducts(popularProducts.stream()
                        .map(productDocumentMapper::toProductSummaryDto)
                        .collect(Collectors.toList()))
                .featuredCategories(featuredCategories)
                .build();
    }
}