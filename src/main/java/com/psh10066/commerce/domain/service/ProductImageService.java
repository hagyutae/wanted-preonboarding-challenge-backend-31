package com.psh10066.commerce.domain.service;

import com.psh10066.commerce.api.dto.request.CreateProductImageRequest;
import com.psh10066.commerce.api.dto.response.CreateProductImageResponse;
import com.psh10066.commerce.domain.model.product.Product;
import com.psh10066.commerce.domain.model.product.ProductImage;
import com.psh10066.commerce.domain.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductImageService {

    private final ProductRepository productRepository;

    @Transactional
    public CreateProductImageResponse createProductImage(Long id, CreateProductImageRequest request) {

        Product product = productRepository.getById(id);

        ProductImage productImage = new ProductImage(
            product,
            request.url(),
            request.altText(),
            request.isPrimary(),
            request.displayOrder(),
            request.optionId() != null ? productRepository.getProductOptionByOptionId(request.optionId()) : null
        );

        productRepository.saveProductImage(productImage);

        return new CreateProductImageResponse(
            productImage.getId(),
            productImage.getUrl(),
            productImage.getAltText(),
            productImage.getIsPrimary(),
            productImage.getDisplayOrder(),
            productImage.getOption() != null ? productImage.getOption().getId() : null
        );
    }
}
