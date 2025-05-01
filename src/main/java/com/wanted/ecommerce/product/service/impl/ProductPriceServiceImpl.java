package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductPrice;
import com.wanted.ecommerce.product.dto.request.ProductPriceRequest;
import com.wanted.ecommerce.product.dto.response.ProductPriceResponse;
import com.wanted.ecommerce.product.repository.ProductPriceRepository;
import com.wanted.ecommerce.product.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {
    private final ProductPriceRepository priceRepository;

    @Override
    public Long saveProductPrice(Product product, ProductPriceRequest priceRequest) {
        ProductPrice productPrice = ProductPrice.of(product, priceRequest.getBasePrice(),
            priceRequest.getSalePrice(), priceRequest.getCostPrice(), priceRequest.getCurrency(),
            priceRequest.getTaxRate());
        ProductPrice savedPrice = priceRepository.save(productPrice);
        return savedPrice.getId();
    }

    @Override
    public ProductPrice findProductPriceByProductId(Long productId) {
        return priceRepository.findByProductId(productId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
    }

    @Override
    public ProductPriceResponse createPriceResponse(ProductPrice price) {
        double basePrice = Double.parseDouble(String.format("%.2f", price.getBasePrice()));
        double salePrice = Double.parseDouble(String.format("%.2f", price.getSalePrice()));
        double discount = basePrice - salePrice;
        double discountPercentage = Double.parseDouble(
            String.format("%.2f", (discount / basePrice) * 100));
        return ProductPriceResponse.of(basePrice,
            salePrice, price.getCurrency(), price.getTaxRate().doubleValue(), discountPercentage);
    }
}
