package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.dto.request.ProductCreateRequest;
import com.wanted.ecommerce.product.dto.response.ProductResponse;
import com.wanted.ecommerce.product.repository.ProductCategoryRepository;
import com.wanted.ecommerce.product.repository.ProductDetailRepository;
import com.wanted.ecommerce.product.repository.ProductImageRepository;
import com.wanted.ecommerce.product.repository.ProductOptionGroupRepository;
import com.wanted.ecommerce.product.repository.ProductOptionRepository;
import com.wanted.ecommerce.product.repository.ProductPriceRepository;
import com.wanted.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductPriceRepository productPriceRepository;

    @Override
    public ProductResponse create(ProductCreateRequest request) {

        return null;
    }
}
