package com.wanted.mono.domain.product.service;

import com.wanted.mono.domain.product.dto.ProductRequest;
import com.wanted.mono.domain.product.dto.response.ProductSaveResponse;
import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.product.repository.ProductRepository;
import com.wanted.mono.domain.tag.service.ProductTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailService productDetailService;
    private final ProductPriceService productPriceService;
    private final ProductCategoryService productCategoryService;
    private final ProductOptionGroupService productOptionGroupService;
    private final ProductImageService productImageService;
    private final ProductTagService productTagService;

    @Transactional
    public ProductSaveResponse createProduct(ProductRequest productRequest) {
        Product product = Product.of(productRequest);

        log.info("Product 서브 엔티티 저장 시작");
        Long savedProductDetailId = productDetailService.createProductDetail(productRequest.getDetail(), product);
        Long savedProductPriceId = productPriceService.createProductPrice(productRequest.getPrice(), product);
        productCategoryService.createProductCategory(productRequest.getCategories(), product);
        productOptionGroupService.createProductOptionGroup(productRequest.getOptionGroups(), product);
        productImageService.createProductImage(productRequest.getImages(), product);
        productTagService.createProductTag(productRequest.getTags(), product);
        log.info("Product 서브 엔티티 저장 종료");

        log.info("Product 엔티티 영속성 저장");
        productRepository.save(product);


        Long productId = product.getId();
        String name = product.getName();
        String slug = product.getSlug();
        LocalDateTime createdAt = product.getCreatedAt();
        LocalDateTime updatedAt = product.getUpdatedAt();
        return new ProductSaveResponse(productId, name, slug, createdAt, updatedAt);
    }
}
