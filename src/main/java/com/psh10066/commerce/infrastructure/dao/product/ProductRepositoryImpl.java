package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.api.dto.request.GetAllProductsRequest;
import com.psh10066.commerce.api.dto.response.GetAllProductsResponse;
import com.psh10066.commerce.domain.exception.ResourceNotFoundException;
import com.psh10066.commerce.domain.model.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductDetailJpaRepository productDetailJpaRepository;
    private final ProductPriceJpaRepository productPriceJpaRepository;
    private final ProductCategoryJpaRepository productCategoryJpaRepository;
    private final ProductOptionGroupJpaRepository productOptionGroupJpaRepository;
    private final ProductOptionJpaRepository productOptionJpaRepository;
    private final ProductImageJpaRepository productImageJpaRepository;
    private final ProductTagJpaRepository productTagJpaRepository;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public ProductDetail saveProductDetail(ProductDetail productDetail) {
        return productDetailJpaRepository.save(productDetail);
    }

    @Override
    public ProductPrice saveProductPrice(ProductPrice productPrice) {
        return productPriceJpaRepository.save(productPrice);
    }

    @Override
    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return productCategoryJpaRepository.save(productCategory);
    }

    @Override
    public ProductOptionGroup saveProductOptionGroup(ProductOptionGroup productOptionGroup) {
        return productOptionGroupJpaRepository.save(productOptionGroup);
    }

    @Override
    public ProductOption saveProductOption(ProductOption productOption) {
        return productOptionJpaRepository.save(productOption);
    }

    @Override
    public ProductImage saveProductImage(ProductImage productImage) {
        return productImageJpaRepository.save(productImage);
    }

    @Override
    public ProductTag saveProductTag(ProductTag productTag) {
        return productTagJpaRepository.save(productTag);
    }

    @Override
    public ProductOption getProductOptionById(Long id) {
        return productOptionJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ProductOption", id));
    }

    @Override
    public Page<GetAllProductsResponse> getAllProducts(GetAllProductsRequest request) {
        return productJpaRepository.getAllProducts(request);
    }
}
