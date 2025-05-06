package com.shopping.mall.main.service;

import com.shopping.mall.category.entity.Category;
import com.shopping.mall.category.repository.CategoryRepository;
import com.shopping.mall.main.dto.response.MainPageResponse;
import com.shopping.mall.product.entity.Product;
import com.shopping.mall.product.entity.ProductImage;
import com.shopping.mall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public MainPageResponse getMainPageProducts() {

        // 1. 신규 상품 (최근 등록 순으로 10개)
        List<Product> newProducts = productRepository.findTop10ByOrderByCreatedAtDesc();

        List<MainPageResponse.ProductSimpleResponse> newProductResponses = newProducts.stream()
                .map(this::toProductSimpleResponse)
                .toList();

        // 2. 카테고리 별 인기 상품 (각 카테고리 별 5개, 지금은 조회수 대신 createdAt기준 임시)
        List<Category> categories = categoryRepository.findAll();

        List<MainPageResponse.CategoryPopularProductsResponse> popularProductsByCategory = categories.stream()
                .map(category -> {
                    List<Product> products = productRepository.findTop5ByCategory(category.getId());

                    return MainPageResponse.CategoryPopularProductsResponse.builder()
                            .categoryId(category.getId())
                            .categoryName(category.getName())
                            .products(products.stream()
                                    .map(this::toProductSimpleResponse)
                                    .toList())
                            .build();
                })
                .toList();

        return MainPageResponse.builder()
                .newProducts(newProductResponses)
                .popularProductsByCategory(popularProductsByCategory)
                .build();
    }

    private MainPageResponse.ProductSimpleResponse toProductSimpleResponse(Product product) {
        String imageUrl = product.getProductImages().stream()
                .filter(ProductImage::getIsPrimary)
                .map(ProductImage::getUrl)
                .findFirst()
                .orElse(null);

        return MainPageResponse.ProductSimpleResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .salePrice(product.getProductPrice().getSalePrice())
                .imageUrl(imageUrl)
                .build();
    }
}
