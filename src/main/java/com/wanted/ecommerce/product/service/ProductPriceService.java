package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductPrice;
import com.wanted.ecommerce.product.dto.request.ProductPriceRequest;
import com.wanted.ecommerce.product.dto.response.ProductPriceResponse;

public interface ProductPriceService {

    Long saveProductPrice(Product product, ProductPriceRequest priceRequest);

    ProductPrice findProductPriceByProductId(Long productId);

    ProductPriceResponse createPriceResponse(ProductPrice price);
}
