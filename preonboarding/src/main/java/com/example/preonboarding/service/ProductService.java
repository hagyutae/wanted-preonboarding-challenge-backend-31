package com.example.preonboarding.service;

import com.example.preonboarding.dto.ProductSearchRequest;
import com.example.preonboarding.dto.ProductsDTO;
import com.example.preonboarding.repository.products.ProductRepository;
import com.example.preonboarding.repository.products.ProductRepositoryCustom;
import com.example.preonboarding.response.BrandResponse;
import com.example.preonboarding.response.ImageResponse;
import com.example.preonboarding.response.ProductsDetailResponse;
import com.example.preonboarding.response.SellerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepositoryCustom productRepositoryCustom;
    public List<ProductsDetailResponse> findAllProducts(ProductSearchRequest search){
        List<ProductsDTO> products = productRepositoryCustom.searchPage(search);

        return products.stream()
                .map(o -> ProductsDetailResponse.builder()
                        .id(o.getId())
                        .name(o.getName())
                        .slug(o.getSlug())
                        .shortDescription(o.getShortDescription())
                        .basePrice(o.getBasePrice())
                        .salesPrice(o.getSalesPrice())
                        .currency(o.getCurrency())
                        .primaryImage(ImageResponse.builder()
                                .url(o.getImages().getImageUrl())
                                .altText(o.getImages().getImageAltText()).build())
                        .brand(BrandResponse.builder()
                                .id(o.getBrandId())
                                .name(o.getBrandName())
                                .build())
                        .seller(SellerResponse.builder()
                                .id(o.getSellerId())
                                .name(o.getSellerName())
                                .build())
                        .rating(o.getReviews().getRating())
                        .reviewCount(o.getReviews().getReviewCount())
                        .stock(o.getOptions().isStock())
                        .status(o.getStatus())
                        .createdAt(o.getCreatedAt())
                        .build()
                ).collect(Collectors.toList());
    }

}
