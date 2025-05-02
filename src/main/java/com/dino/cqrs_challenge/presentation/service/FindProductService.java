package com.dino.cqrs_challenge.presentation.service;

import com.dino.cqrs_challenge.domain.entity.Product;
import com.dino.cqrs_challenge.domain.entity.ProductDetail;
import com.dino.cqrs_challenge.domain.entity.ProductImage;
import com.dino.cqrs_challenge.domain.entity.ProductPrice;
import com.dino.cqrs_challenge.domain.entity.Review;
import com.dino.cqrs_challenge.domain.repository.ProductDetailRepository;
import com.dino.cqrs_challenge.domain.repository.ProductImageRepository;
import com.dino.cqrs_challenge.domain.repository.ProductPriceRepository;
import com.dino.cqrs_challenge.domain.repository.ProductRepository;
import com.dino.cqrs_challenge.domain.repository.ReviewRepository;
import com.dino.cqrs_challenge.global.response.PaginatedApiResponse;
import com.dino.cqrs_challenge.presentation.exception.ProductNotFoundException;
import com.dino.cqrs_challenge.presentation.model.dto.FindProductDTO;
import com.dino.cqrs_challenge.presentation.model.dto.FindProductSearchDTO;
import com.dino.cqrs_challenge.presentation.model.rq.ProductSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductPriceRepository productPriceRepository;
    private final ReviewRepository reviewRepository;
    private final ProductImageRepository productImageRepository;

    public PaginatedApiResponse<FindProductSearchDTO> findProductsBySearchCondition(ProductSearchCondition productSearchCondition) {
        Page<Product> result = productRepository.searchProducts(productSearchCondition);

        List<Product> productList = result.getContent();

        List<Long> productIdList = productList.stream()
                .map(Product::getId)
                .toList();

        Map<Long, ProductImage> primaryProductImageMap = productImageRepository.findAllByProductIdInAndIsPrimary(productIdList, true).stream()
                .collect(
                        Collectors.toMap(
                                productImage -> productImage.getProduct().getId(),
                                Function.identity(),
                                (o1, o2) -> o1
                        )
                );

        Map<Long, ProductPrice> productPriceMap = productPriceRepository.findAllByProductIdIn(productIdList).stream()
                .collect(Collectors.toMap(
                        productPrice -> productPrice.getProduct().getId(),
                        Function.identity(),
                        (o1, o2) -> o1
                ));
        Map<Long, List<Review>> reviewListMapGroupingByProductId = reviewRepository.findAllByProductIdIn(productIdList)
                .stream()
                .collect(Collectors.groupingBy(
                        review -> review.getProduct().getId()));

        List<FindProductSearchDTO> productSearchDTOList = new ArrayList<>();
        for (Product product : productList) {
            List<Review> reviewList = reviewListMapGroupingByProductId.getOrDefault(product.getId(), Collections.emptyList());
            ProductPrice productPrice = productPriceMap.get(product.getId());
            ProductImage primaryImage = primaryProductImageMap.get(product.getId());
            productSearchDTOList.add(FindProductSearchDTO.of(product, productPrice, reviewList, primaryImage));

        }
        return PaginatedApiResponse.<FindProductSearchDTO>builder()
                .items(productSearchDTOList)
                .pagination(PaginatedApiResponse.Pagination.builder()
                        .totalItems(result.getTotalElements())
                        .totalPages(result.getTotalPages())
                        .currentPage(result.getNumber() + 1)
                        .perPage(result.getSize())
                        .build())
                .build();
    }

    public FindProductDTO findProductById(Long id) {
        Product product = findProductByIdThrowIfNull(id);
        ProductDetail productDetail = productDetailRepository.findByProductId(id);
        ProductPrice productPrice = productPriceRepository.findByProductId(id);
        return FindProductDTO.of(product, productDetail, productPrice);
    }

    public Product findProductByIdThrowIfNull(Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }
}
