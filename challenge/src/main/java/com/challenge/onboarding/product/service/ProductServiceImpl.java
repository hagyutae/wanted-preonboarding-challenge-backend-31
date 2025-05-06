package com.challenge.onboarding.product.service;

import com.challenge.onboarding.category.model.ProductCategory;
import com.challenge.onboarding.category.repository.CategoryRepository;
import com.challenge.onboarding.category.repository.ProductCategoryRepository;
import com.challenge.onboarding.product.domain.*;
import com.challenge.onboarding.product.repository.*;
import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import com.challenge.onboarding.product.service.dto.response.CreateProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final BrandRepository brandRepository;
    private final SellerRepository sellerRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductPriceRepository productPriceRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public CreateProductResponse createProduct(CreateProductRequest request) {
        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Seller seller = sellerRepository.findById(request.sellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        Product product = Product.from(request, seller, brand);
        createAdditionalProductInfo(request, product);
        return null;
    }

    protected void createAdditionalProductInfo(CreateProductRequest request, Product product) {
        saveDetail(request, product);
        savePrice(request, product);
        saveCategories(request, product);
        saveOptionGroups(request, product);
        saveTags(request, product);
    }

    private void saveDetail(CreateProductRequest request, Product product) {
        ProductDetail detail = ProductDetail.generateProductDetail(request, product);
        productDetailRepository.save(detail);
    }


    private void savePrice(CreateProductRequest request, Product product) {
        ProductPrice price = ProductPrice.from(request, product);
        productPriceRepository.save(price);
    }

    private void saveCategories(CreateProductRequest request, Product product) {
        request.categories().forEach(c -> {
            categoryRepository.findById(c.categoryId()).ifPresent(category -> {
                ProductCategory pc = ProductCategory.from(c, product, category);
                productCategoryRepository.save(pc);
            });
        });
    }

    private void saveOptionGroups(CreateProductRequest request, Product product) {
        request.optionGroups().forEach(group -> {
            ProductOptionGroup optionGroup = ProductOptionGroup.from(group, product);
            List<ProductOption> options = ProductOption.initListOption(group);
            productOptionGroupRepository.save(optionGroup);
            productOptionRepository.saveAll(options);
        });
    }

    private void saveTags(CreateProductRequest request, Product product) {
        request.tags().forEach(tagId ->
                tagRepository.findById(tagId)
                        .map(tag -> ProductTag.from(product, tag))
                        .ifPresent(productTagRepository::save)
        );
    }

}
