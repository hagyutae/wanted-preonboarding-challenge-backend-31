package com.challenge.onboarding.product.service;

import com.challenge.onboarding.category.model.ProductCategory;
import com.challenge.onboarding.category.repository.CategoryRepository;
import com.challenge.onboarding.category.repository.ProductCategoryRepository;
import com.challenge.onboarding.product.domain.model.*;
import com.challenge.onboarding.product.repository.*;
import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import com.challenge.onboarding.product.service.dto.request.ProductSearchCondition;
import com.challenge.onboarding.product.service.dto.response.CreateProductResponse;
import com.challenge.onboarding.product.service.dto.response.ProductListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductListResponse getProductList(ProductSearchCondition request) {
        Pageable pageable = PageRequest.of(
                request.page() - 1,
                request.perPage(),
                parseSort(request.sort())
        );
        Page<Product> products = productRepository.findAll(pageable);
        ProductPrice price = products.getContent()



        Product product = productRepository.findAll(pageable)
                .getContent()
                .stream()
                .filter(p -> request.status() == null || p.getStatus().name().equalsIgnoreCase(request.status()))
                .filter(p -> request.minPrice() == null || p.getPrice().getPrice() >= request.minPrice())
                .filter(p -> request.maxPrice() == null || p.getPrice().getPrice() <= request.maxPrice())
                .filter(p -> p.isAvailable(request.inStock()))
                .filter(p -> request.search() == null || p.getName().contains(request.search()))
                .collect(Collectors.toList());

        return new ProductListResponse();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateProductResponse createProduct(CreateProductRequest request) {
        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Seller seller = sellerRepository.findById(request.sellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        Product product = productRepository.save(Product.from(request, seller, brand));
        createAdditionalProductInfo(request, product);
        return new CreateProductResponse(product);
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

    private Sort parseSort(String sortParam) {
        if (sortParam == null || sortParam.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        String[] sortParts = sortParam.split(",");
        List<Sort.Order> orders = new ArrayList<>();

        for (String part : sortParts) {
            String[] fieldAndDir = part.split(":");
            if (fieldAndDir.length == 2) {
                String field = convertToCamelCase(fieldAndDir[0]);
                Sort.Direction dir = fieldAndDir[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
                orders.add(Sort.Order.by(field).with(dir));
            }
        }

        return Sort.by(orders);
    }

    private String convertToCamelCase(String snake) {
        return Arrays.stream(snake.split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase())
                .collect(Collectors.joining())
                .replaceFirst("^[A-Z]", String::toLowerCase); // created_at -> createdAt
    }

}
