package com.psh10066.commerce.domain.service;

import com.psh10066.commerce.api.common.PaginationResponse;
import com.psh10066.commerce.api.dto.request.CreateProductRequest;
import com.psh10066.commerce.api.dto.request.GetAllProductsRequest;
import com.psh10066.commerce.api.dto.request.UpdateProductRequest;
import com.psh10066.commerce.api.dto.response.CreateProductResponse;
import com.psh10066.commerce.api.dto.response.GetAllProductsResponse;
import com.psh10066.commerce.api.dto.response.GetProductDetailResponse;
import com.psh10066.commerce.api.dto.response.UpdateProductResponse;
import com.psh10066.commerce.domain.model.brand.Brand;
import com.psh10066.commerce.domain.model.brand.BrandRepository;
import com.psh10066.commerce.domain.model.category.Category;
import com.psh10066.commerce.domain.model.category.CategoryRepository;
import com.psh10066.commerce.domain.model.product.*;
import com.psh10066.commerce.domain.model.review.ReviewFirstCollection;
import com.psh10066.commerce.domain.model.review.ReviewRepository;
import com.psh10066.commerce.domain.model.seller.Seller;
import com.psh10066.commerce.domain.model.seller.SellerRepository;
import com.psh10066.commerce.domain.model.tag.Tag;
import com.psh10066.commerce.domain.model.tag.TagRepository;
import jakarta.validation.Valid;
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
    private final ReviewRepository reviewRepository;

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

    public GetProductDetailResponse getProductDetail(Long id) {
        Product product = productRepository.getById(id);
        Seller seller = product.getSeller();
        Brand brand = product.getBrand();
        ProductDetail productDetail = productRepository.getProductDetailById(id);
        ProductDetail.Dimensions dimensions = productDetail.getDimensions();
        ProductPrice productPrice = productRepository.getProductPriceById(id);
        List<ProductCategory> productCategories = productRepository.findProductCategoriesById(id);
        List<ProductOptionGroup> productOptionGroups = productRepository.findProductOptionGroupsById(id);
        List<ProductImage> productImages = productRepository.findProductImagesById(id);
        List<ProductTag> productTags = productRepository.findProductTagsById(id);
        ReviewFirstCollection reviews = new ReviewFirstCollection(reviewRepository.findAllByProductId(product.getId()));

        return GetProductDetailResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .slug(product.getSlug())
            .shortDescription(product.getShortDescription())
            .fullDescription(product.getFullDescription())
            .seller(new GetProductDetailResponse.Seller(
                seller.getId(), seller.getName(), seller.getDescription(), seller.getLogoUrl(), seller.getRating(), seller.getContactEmail(), seller.getContactPhone()
            ))
            .brand(new GetProductDetailResponse.Brand(
                brand.getId(), brand.getName(), brand.getDescription(), brand.getLogoUrl(), brand.getWebsite()
            ))
            .status(product.getStatus())
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getUpdatedAt())
            .detail(new GetProductDetailResponse.Detail(
                productDetail.getWeight(),
                new GetProductDetailResponse.Dimensions(dimensions.getWidth(), dimensions.getHeight(), dimensions.getDepth()),
                productDetail.getMaterials(),
                productDetail.getCountryOfOrigin(),
                productDetail.getWarrantyInfo(),
                productDetail.getCareInstructions(),
                productDetail.getAdditionalInfo()
            ))
            .price(new GetProductDetailResponse.Price(
                productPrice.getBasePrice(), productPrice.getSalePrice(), productPrice.getCurrency(), productPrice.getTaxRate(), productPrice.calculateDiscountPercentage()
            ))
            .categories(productCategories.stream()
                .map(productCategory -> {
                    Category category = productCategory.getCategory();
                    Category parentCategory = category.getParent();
                    return new GetProductDetailResponse.Category(
                        category.getId(), category.getName(), category.getSlug(), productCategory.getIsPrimary(),
                        new GetProductDetailResponse.ParentCategory(parentCategory.getId(), parentCategory.getName(), parentCategory.getSlug())
                    );
                })
                .toList())
            .optionGroups(productOptionGroups.stream()
                .map(productOptionGroup -> {
                    List<ProductOption> productOptions = productRepository.findProductOptionsByProductOptionGroupId(productOptionGroup.getId());
                    return new GetProductDetailResponse.OptionGroup(
                        productOptionGroup.getId(), productOptionGroup.getName(), productOptionGroup.getDisplayOrder(),
                        productOptions.stream()
                            .map(productOption -> new GetProductDetailResponse.Option(
                                productOption.getId(), productOption.getName(), productOption.getAdditionalPrice(), productOption.getSku(), productOption.getStock(), productOption.getDisplayOrder()
                            ))
                            .toList()
                    );
                })
                .toList())
            .images(productImages.stream()
                .map(productImage -> new GetProductDetailResponse.Image(
                    productImage.getId(), productImage.getUrl(), productImage.getAltText(), productImage.getIsPrimary(), productImage.getDisplayOrder(), productImage.getOption() != null ? productImage.getOption().getId() : null
                ))
                .toList())
            .tags(productTags.stream()
                .map(productTag -> {
                    Tag tag = productTag.getTag();
                    return new GetProductDetailResponse.Tag(tag.getId(), tag.getName(), tag.getSlug());
                })
                .toList())
            .rating(new GetProductDetailResponse.Rating(
                reviews.getAverage(),
                reviews.getSize(),
                reviews.getDistribution()
            ))
//            .relatedProducts() // TODO
            .build();
    }

    @Transactional
    public UpdateProductResponse updateProduct(Long id, @Valid UpdateProductRequest request) {

        Product product = productRepository.getById(id);

        Seller seller = sellerRepository.getById(request.sellerId());
        Brand brand = brandRepository.getById(request.brandId());

        product.update(
            request.name(),
            request.slug(),
            request.shortDescription(),
            request.fullDescription(),
            seller,
            brand,
            request.status()
        );

        productRepository.save(product);

        UpdateProductRequest.ProductDetailRequest requestDetail = request.detail();

        productRepository.getProductDetailById(id);
        ProductDetail productDetail = productRepository.getProductDetailById(id);
        productDetail.update(
            requestDetail.weight(),
            new ProductDetail.Dimensions(
                requestDetail.dimensions().width(),
                requestDetail.dimensions().height(),
                requestDetail.dimensions().depth()
            ),
            requestDetail.materials(),
            requestDetail.countryOfOrigin(),
            requestDetail.warrantyInfo(),
            requestDetail.careInstructions(),
            requestDetail.additionalInfo()
        );

        productRepository.saveProductDetail(productDetail);

        UpdateProductRequest.ProductPriceRequest requestPrice = request.price();
        ProductPrice productPrice = productRepository.getProductPriceById(id);
        productPrice.update(
            requestPrice.basePrice(),
            requestPrice.salePrice(),
            requestPrice.costPrice(),
            requestPrice.currency(),
            requestPrice.taxRate()
        );

        productRepository.saveProductPrice(productPrice);

        List<UpdateProductRequest.ProductCategoryRequest> requestCategories = request.categories();

        productRepository.deleteProductCategoryById(id);
        requestCategories.forEach(requestCategory -> {
            Category category = categoryRepository.getById(requestCategory.categoryId());
            productRepository.saveProductCategory(new ProductCategory(product, category, requestCategory.isPrimary()));
        });

        // TODO
//        List<UpdateProductRequest.ProductOptionGroupRequest> requestOptionGroups = request.optionGroups();
//        requestOptionGroups.forEach(requestOptionGroup -> {
//            ProductOptionGroup productOptionGroup = productRepository.saveProductOptionGroup(new ProductOptionGroup(product, requestOptionGroup.name(), requestOptionGroup.displayOrder()));
//            requestOptionGroup.options().forEach(requestOption -> {
//                productRepository.saveProductOption(new ProductOption(productOptionGroup, requestOption.name(), requestOption.additionalPrice(), requestOption.sku(), requestOption.stock(), requestOption.displayOrder()));
//            });
//        });
//
//        List<UpdateProductRequest.ProductImageRequest> requestImages = request.images();
//        requestImages.forEach(requestImage -> {
//            productRepository.saveProductImage(new ProductImage(
//                product,
//                requestImage.url(),
//                requestImage.altText(),
//                requestImage.isPrimary(),
//                requestImage.displayOrder(),
//                requestImage.optionId() != null ? productRepository.getProductOptionById(requestImage.optionId()) : null
//            ));
//        });
//
//        List<Long> requestTagIds = request.tags();
//        requestTagIds.forEach(tagId -> {
//            Tag tag = tagRepository.getById(tagId);
//            productRepository.saveProductTag(new ProductTag(product, tag));
//        });

        return new UpdateProductResponse(
            product.getId(),
            product.getName(),
            product.getSlug(),
            product.getUpdatedAt()
        );
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.getById(id);
        product.delete();
        productRepository.save(product);
    }
}
