package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.brand.domain.Brand;
import com.wanted.ecommerce.brand.repository.BrandRepository;
import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.category.repository.CategoryRepository;
import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductCategory;
import com.wanted.ecommerce.product.domain.ProductDetail;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.domain.ProductPrice;
import com.wanted.ecommerce.product.domain.ProductStatus;
import com.wanted.ecommerce.product.domain.ProductTag;
import com.wanted.ecommerce.product.dto.request.ProductCategoryRequest;
import com.wanted.ecommerce.product.dto.request.ProductCreateRequest;
import com.wanted.ecommerce.product.dto.request.ProductDetailRequest;
import com.wanted.ecommerce.product.dto.request.ProductImageRequest;
import com.wanted.ecommerce.product.dto.request.ProductOptionGroupRequest;
import com.wanted.ecommerce.product.dto.request.ProductPriceRequest;
import com.wanted.ecommerce.product.dto.response.ProductResponse;
import com.wanted.ecommerce.product.repository.ProductCategoryRepository;
import com.wanted.ecommerce.product.repository.ProductDetailRepository;
import com.wanted.ecommerce.product.repository.ProductImageRepository;
import com.wanted.ecommerce.product.repository.ProductOptionGroupRepository;
import com.wanted.ecommerce.product.repository.ProductOptionRepository;
import com.wanted.ecommerce.product.repository.ProductPriceRepository;
import com.wanted.ecommerce.product.repository.ProductRepository;
import com.wanted.ecommerce.product.repository.ProductTagRepository;
import com.wanted.ecommerce.seller.domain.Seller;
import com.wanted.ecommerce.seller.repository.SellerRepository;
import com.wanted.ecommerce.tag.domain.Tag;
import com.wanted.ecommerce.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
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
    private final ProductTagRepository productTagRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Transactional
    @Override
    public ProductResponse create(ProductCreateRequest request) {
        Seller seller = sellerRepository.findById(request.getSellerId())
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));

        Brand brand = brandRepository.findById(request.getBrandId())
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));

        Product product = Product.of(
            request.getName(),
            request.getSlug(),
            request.getShortDescription(),
            request.getFullDescription(),
            seller,
            brand,
            ProductStatus.valueOf(request.getStatus())
        );

        Product saved = productRepository.save(product);

        createProductCategories(saved, request.getCategories());
        createProductDetail(saved, request.getDetail());
        createProductImages(saved, request.getImages());
        createProductOptions(saved, request.getOptionGroups());
        createProductPrice(saved, request.getPrice());
        createProductTags(saved, request.getTags());
        return ProductResponse.of(saved.getId(), saved.getName(), saved.getSlug(),
            saved.getCreatedAt(), saved.getUpdatedAt());
    }

    private List<Long> createProductCategories(Product product,
        List<ProductCategoryRequest> categoryRequestList) {
        List<Long> categoryIds = categoryRequestList.stream()
            .map(ProductCategoryRequest::getCategoryId)
            .toList();
        Map<Long, Category> categoryMap = categoryRepository.findAllById(categoryIds).stream()
            .collect(Collectors.toMap(Category::getId, Function.identity()));

        List<ProductCategory> productCategories = categoryRequestList.stream()
            .map(categoryRequest -> {
                Long categoryId = categoryRequest.getCategoryId();
                Category category = categoryMap.get(categoryId);
                return ProductCategory.of(product, category, categoryRequest.getIsPrimary());
            })
            .toList();

        List<ProductCategory> savedCategories = productCategoryRepository.saveAll(
            productCategories);
        return savedCategories.stream().map(ProductCategory::getId).toList();
    }

    private Long createProductDetail(Product product, ProductDetailRequest detailRequest) {
        ProductDetail detail = ProductDetail.of(product, detailRequest.getWeight(),
            detailRequest.getDimensionsRequest(), detailRequest.getMaterials(),
            detailRequest.getCountryOfOrigin(), detailRequest.getWarrantyInfo(),
            detailRequest.getCareInstructions(), detailRequest.getAdditionalInfoRequest());
        ProductDetail saved = productDetailRepository.save(detail);
        return saved.getId();
    }

    private List<Long> createProductImages(Product product,
        List<ProductImageRequest> imageRequestList) {
        List<ProductImage> images = imageRequestList.stream().map(imageRequest ->
        {
            ProductOption option = Optional.ofNullable(imageRequest.getOptionId())
                .flatMap(productOptionRepository::findById)
                .orElse(null);
            return ProductImage.of(product, imageRequest.getUrl(), imageRequest.getAltText(),
                imageRequest.getIsPrimary(), imageRequest.getDisplayOrder(), option);
        }).toList();
        List<ProductImage> savedImages = productImageRepository.saveAll(images);
        return savedImages.stream().map(ProductImage::getId).toList();
    }

    private List<Long> createProductOptions(Product saved,
        List<ProductOptionGroupRequest> optionGroups) {
        return optionGroups.stream()
            .map(groupRequest -> {
                ProductOptionGroup optionGroup = ProductOptionGroup.of(saved,
                    groupRequest.getName(), groupRequest.getDisplayOrder());
                ProductOptionGroup savedGroup = productOptionGroupRepository.save(optionGroup);

                List<ProductOption> options = groupRequest.getOptions().stream()
                    .map(optionRequest -> ProductOption.of(
                        savedGroup,
                        optionRequest.getName(),
                        optionRequest.getAdditionalPrice(),
                        optionRequest.getSku(),
                        optionRequest.getStock(),
                        optionRequest.getDisplayOrder()
                    ))
                    .toList();
                productOptionRepository.saveAll(options);
                return savedGroup.getId();
            })
            .toList();
    }

    private long createProductPrice(Product saved, ProductPriceRequest priceRequest) {
        ProductPrice productPrice = ProductPrice.of(saved, priceRequest.getBasePrice(),
            priceRequest.getSalePrice(), priceRequest.getCostPrice(), priceRequest.getCurrency(),
            priceRequest.getTaxRate());
        ProductPrice savedPrice = productPriceRepository.save(productPrice);
        return savedPrice.getId();
    }

    private List<Long> createProductTags(Product saved, List<Long> tagIds) {
        List<Tag> tags = tagIds.stream().map(tagId -> {
            return tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
        }).toList();

        List<ProductTag> savedTagList = tags.stream().map(tag -> {
            ProductTag productTag = ProductTag.of(saved, tag);
            return productTagRepository.save(productTag);
        }).toList();

        return savedTagList.stream().map(ProductTag::getId).toList();
    }
}
