package com.example.demo.product.service;

import com.example.demo.product.dto.FeaturedCategory;
import com.example.demo.product.dto.MainProductSummary;
import com.example.demo.product.entity.ProductEntity;
import com.example.demo.product.entity.ProductImageEntity;
import com.example.demo.product.repository.ProductCategoryRepository;
import com.example.demo.product.repository.ProductImageRepository;
import com.example.demo.product.repository.ProductRepository;
import com.example.demo.productoption.dto.ProductStock;
import com.example.demo.productoption.repository.ProductOptionRepository;
import com.example.demo.review.dto.ReviewStatistic;
import com.example.demo.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MainService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ReviewService reviewService;

    @Transactional(readOnly = true)
    public MainProductSummary search() {
        List<ProductEntity> latestProductEntityList = productRepository.findTop10LatestList();
        List<Long> hotTop10ProductIds = reviewService.findHotTop10ProductIds();
        List<ProductEntity> HotProductEntityList = productRepository.findAllByIds(hotTop10ProductIds);
        List<FeaturedCategory> featuredCategories = productCategoryRepository.findFeaturedCategories();

        Set<Long> productIds = latestProductEntityList.stream().map(ProductEntity::getId)
                .collect(Collectors.toSet());
        productIds.addAll(hotTop10ProductIds);

        List<ProductImageEntity> productPrimaryImages = productImageRepository.findAllByProductIdsAndIsPrimary(productIds);
        Map<Long, ProductImageEntity> productPrimaryImageMap = productPrimaryImages.stream()
                .collect(Collectors.toMap(
                        image -> image.getProductEntity().getId(),
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
        List<ReviewStatistic> reviewStatistics = reviewService.findStatisticsByProductIds(productIds);
        Map<Long, ReviewStatistic> reviewStatisticMap = reviewStatistics.stream()
                .collect(Collectors.toMap(
                        ReviewStatistic::productId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
        List<ProductStock> productStocks = productOptionRepository.findAllByProductIds(productIds);
        Map<Long, ProductStock> productStockMap = productStocks.stream()
                .collect(Collectors.toMap(
                        ProductStock::productId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        return MainProductSummary.of(
                latestProductEntityList,
                HotProductEntityList,
                featuredCategories,
                productPrimaryImageMap,
                reviewStatisticMap,
                productStockMap
        );
    }
}
