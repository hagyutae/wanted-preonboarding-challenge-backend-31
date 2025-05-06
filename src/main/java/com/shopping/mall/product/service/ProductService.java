package com.shopping.mall.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.mall.brand.entity.Brand;
import com.shopping.mall.brand.repository.BrandRepository;
import com.shopping.mall.category.entity.Category;
import com.shopping.mall.category.repository.CategoryRepository;
import com.shopping.mall.product.dto.request.ProductCreateRequest;
import com.shopping.mall.product.dto.request.ProductUpdateRequest;
import com.shopping.mall.product.entity.*;
import com.shopping.mall.product.repository.ProductCategoryRepository;
import com.shopping.mall.product.repository.ProductDetailRepository;
import com.shopping.mall.product.repository.ProductPriceRepository;
import com.shopping.mall.product.repository.ProductRepository;
import com.shopping.mall.seller.entity.Seller;
import com.shopping.mall.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductPriceRepository productPriceRepository;
    private final ProductCategoryRepository productCategoryRepository;

    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public Long createProduct(ProductCreateRequest request) {

        Seller seller = sellerRepository.findById(request.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("판매자가 존재하지 않습니다"));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("브랜드가 존재하지 않습니다"));

        // Product
        Product product = Product.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .shortDescription(request.getShortDescription())
                .fullDescription(request.getFullDescription())
                .seller(seller)
                .brand(brand)
                .status(ProductStatus.valueOf(request.getStatus()))
                .build();

        productRepository.save(product);

        // Product Detail
        ProductDetail detail = ProductDetail.builder()
                .product(product)
                .weight(request.getDetail().getWeight())
                .dimensions(toJson(request.getDetail().getDimensions()))
                .materials(request.getDetail().getMaterials())
                .countryOfOrigin(request.getDetail().getCountryOfOrigin())
                .warrantyInfo(request.getDetail().getWarrantyInfo())
                .careInstructions(request.getDetail().getCareInstructions())
                .additionalInfo(toJson(request.getDetail().getAdditionalInfo()))
                .build();

        productDetailRepository.save(detail);

        // Product Price
        ProductPrice price = ProductPrice.builder()
                .product(product)
                .basePrice(request.getPrice().getBasePrice())
                .salePrice(request.getPrice().getSalePrice())
                .costPrice(request.getPrice().getCostPrice())
                .currency(request.getPrice().getCurrency())
                .taxRate(request.getPrice().getTaxRate())
                .build();

        productPriceRepository.save(price);

        // Product Category
        for (ProductCreateRequest.CategoryInfo categoryInfo : request.getCategories()) {
            Category category = categoryRepository.findById(categoryInfo.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다"));

            ProductCategory productCategory = ProductCategory.builder()
                    .product(product)
                    .category(category)
                    .isPrimary(categoryInfo.getIsPrimary())
                    .build();

            productCategoryRepository.save(productCategory);
        }

        return product.getId();
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void updateProduct(Long productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        product.update(
                request.getName(),
                request.getSlug(),
                request.getShortDescription(),
                request.getFullDescription(),
                request.getStatus()
        );
    }
}
