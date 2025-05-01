package com.example.preonboarding.images.service;

import com.example.preonboarding.exception.NotFoundResourceException;
import com.example.preonboarding.images.domain.ProductImages;
import com.example.preonboarding.images.dto.ProductImageDTO;
import com.example.preonboarding.images.repository.ProductImageRepository;
import com.example.preonboarding.options.domain.ProductOption;
import com.example.preonboarding.options.repository.ProductOptionRepository;
import com.example.preonboarding.products.domain.Products;
import com.example.preonboarding.products.repository.ProductRepository;
import com.example.preonboarding.response.error.ErrorCode;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final EntityManager em;

    @Transactional
    public ProductImages addProductImage(Long id, ProductImageDTO request){

        Products products = productRepository.findById(id).orElseThrow(() -> new NotFoundResourceException(ErrorCode.RESOURCE_NOT_FOUND));

        ProductOption productOption = productOptionRepository.findById(request.getOptionId()).orElseThrow(() -> new IllegalArgumentException("not found option"));

        if (request.isPrimary()) {
            productImageRepository.clearPrimaryImageByProductId(id);
        }

        ProductImages newProductImage = ProductImages.builder()
                .products(products)
                .url(request.getUrl())
                .altText(request.getAltText())
                .isPrimary(request.isPrimary())
                .displayOrder(request.getDisplayOrder())
                .options(productOption)
                .build();


        ProductImages saveImages = productImageRepository.save(newProductImage);
        em.flush();
        em.clear();

        return saveImages;
    }
}
