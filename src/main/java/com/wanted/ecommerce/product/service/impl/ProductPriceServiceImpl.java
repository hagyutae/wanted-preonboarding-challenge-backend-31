package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductPrice;
import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.ProductPriceRequest;
import com.wanted.ecommerce.product.dto.response.ProductResponse.ProductPriceResponse;
import com.wanted.ecommerce.product.repository.ProductPriceRepository;
import com.wanted.ecommerce.product.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {
    private final ProductPriceRepository priceRepository;

    @Transactional
    @Override
    public Long saveProductPrice(Product product, ProductPriceRequest priceRequest) {
        ProductPrice productPrice = ProductPrice.of(product, priceRequest);
        ProductPrice savedPrice = priceRepository.save(productPrice);
        return savedPrice.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductPrice findProductPriceByProductId(Long productId) {
        return priceRepository.findByProductId(productId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
    }

    @Transactional
    @Override
    public void updatePrice(ProductPrice price, ProductPriceRequest request) {
        price.update(request);
    }

    @Override
    public ProductPriceResponse createPriceResponse(ProductPrice price) {
        return ProductPriceResponse.of(price);
    }
}
