package com.psh10066.commerce.domain.service;

import com.psh10066.commerce.api.common.PaginationResponse;
import com.psh10066.commerce.api.dto.request.CreateProductRequest;
import com.psh10066.commerce.api.dto.request.GetAllProductsRequest;
import com.psh10066.commerce.api.dto.response.CreateProductResponse;
import com.psh10066.commerce.api.dto.response.GetAllProductsResponse;
import com.psh10066.commerce.domain.model.brand.Brand;
import com.psh10066.commerce.domain.model.brand.BrandRepository;
import com.psh10066.commerce.domain.model.category.Category;
import com.psh10066.commerce.domain.model.category.CategoryRepository;
import com.psh10066.commerce.domain.model.product.*;
import com.psh10066.commerce.domain.model.seller.Seller;
import com.psh10066.commerce.domain.model.seller.SellerRepository;
import com.psh10066.commerce.domain.model.tag.Tag;
import com.psh10066.commerce.domain.model.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Transactional
    public CreateProductResponse createProduct(CreateProductRequest request) {

        Seller seller = sellerRepository.getById(request.sellerId());
        Brand brand = brandRepository.getById(request.brandId());

        Product product = Product.builder()
            .name(request.name())
            .slug(request.slug())
            .shortDescription(request.shortDescription())
            .fullDescription(request.fullDescription())
            .seller(seller)
            .brand(brand)
            .status(request.status())
            .build();

        productRepository.save(product);

        CreateProductRequest.ProductDetailRequest requestDetail = request.detail();
        ProductDetail productDetail = ProductDetail.builder()
            .product(product)
            .weight(requestDetail.weight())
            .dimensions(new ProductDetail.Dimensions(
                requestDetail.dimensions().width(),
                requestDetail.dimensions().height(),
                requestDetail.dimensions().depth()
            ))
            .materials(requestDetail.materials())
            .countryOfOrigin(requestDetail.countryOfOrigin())
            .warrantyInfo(requestDetail.warrantyInfo())
            .careInstructions(requestDetail.careInstructions())
            .additionalInfo(requestDetail.additionalInfo())
            .build();

        productRepository.saveProductDetail(productDetail);

        CreateProductRequest.ProductPriceRequest requestPrice = request.price();
        ProductPrice productPrice = ProductPrice.builder()
            .product(product)
            .basePrice(requestPrice.basePrice())
            .salePrice(requestPrice.salePrice())
            .costPrice(requestPrice.costPrice())
            .currency(requestPrice.currency())
            .taxRate(requestPrice.taxRate())
            .build();

        productRepository.saveProductPrice(productPrice);

        List<CreateProductRequest.ProductCategoryRequest> requestCategories = request.categories();
        requestCategories.forEach(requestCategory -> {
            Category category = categoryRepository.getById(requestCategory.categoryId());
            productRepository.saveProductCategory(new ProductCategory(product, category, requestCategory.isPrimary()));
        });

        List<CreateProductRequest.ProductOptionGroupRequest> requestOptionGroups = request.optionGroups();
        requestOptionGroups.forEach(requestOptionGroup -> {
            ProductOptionGroup productOptionGroup = productRepository.saveProductOptionGroup(new ProductOptionGroup(product, requestOptionGroup.name(), requestOptionGroup.displayOrder()));
            requestOptionGroup.options().forEach(requestOption -> {
                productRepository.saveProductOption(new ProductOption(productOptionGroup, requestOption.name(), requestOption.additionalPrice(), requestOption.sku(), requestOption.stock(), requestOption.displayOrder()));
            });
        });

        List<CreateProductRequest.ProductImageRequest> requestImages = request.images();
        requestImages.forEach(requestImage -> {
            productRepository.saveProductImage(new ProductImage(
                product,
                requestImage.url(),
                requestImage.altText(),
                requestImage.isPrimary(),
                requestImage.displayOrder(),
                requestImage.optionId() != null ? productRepository.getProductOptionById(requestImage.optionId()) : null
            ));
        });

        List<Long> requestTagIds = request.tags();
        requestTagIds.forEach(tagId -> {
            Tag tag = tagRepository.getById(tagId);
            productRepository.saveProductTag(new ProductTag(product, tag));
        });

        return new CreateProductResponse(
            product.getId(),
            product.getName(),
            product.getSlug(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }

    public PaginationResponse<GetAllProductsResponse> getAllProducts(GetAllProductsRequest request) {
        Page<GetAllProductsResponse> products = productRepository.getAllProducts(request);
        return PaginationResponse.of(products, product -> product);
    }
}
