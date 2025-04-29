package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.brand.domain.Brand;
import com.wanted.ecommerce.brand.dto.response.BrandDetailResponse;
import com.wanted.ecommerce.brand.dto.response.BrandResponse;
import com.wanted.ecommerce.brand.repository.BrandRepository;
import com.wanted.ecommerce.category.domain.Category;
import com.wanted.ecommerce.category.dto.response.CategoryResponse;
import com.wanted.ecommerce.category.dto.response.ParentCategoryResponse;
import com.wanted.ecommerce.category.repository.CategoryRepository;
import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.product.domain.Dimensions;
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
import com.wanted.ecommerce.product.dto.request.ProductReadAllRequest;
import com.wanted.ecommerce.product.dto.response.DetailResponse;
import com.wanted.ecommerce.product.dto.response.DimensionsResponse;
import com.wanted.ecommerce.product.dto.response.ProductDetailImageResponse;
import com.wanted.ecommerce.product.dto.response.ProductDetailResponse;
import com.wanted.ecommerce.product.dto.response.ProductImageResponse;
import com.wanted.ecommerce.product.dto.response.ProductListResponse;
import com.wanted.ecommerce.product.dto.response.ProductOptionGroupResponse;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import com.wanted.ecommerce.product.dto.response.ProductPriceResponse;
import com.wanted.ecommerce.product.dto.response.ProductResponse;
import com.wanted.ecommerce.product.dto.response.RelatedProductResponse;
import com.wanted.ecommerce.product.repository.ProductCategoryRepository;
import com.wanted.ecommerce.product.repository.ProductDetailRepository;
import com.wanted.ecommerce.product.repository.ProductImageRepository;
import com.wanted.ecommerce.product.repository.ProductOptionGroupRepository;
import com.wanted.ecommerce.product.repository.ProductOptionRepository;
import com.wanted.ecommerce.product.repository.ProductPriceRepository;
import com.wanted.ecommerce.product.repository.ProductRepository;
import com.wanted.ecommerce.product.repository.ProductTagRepository;
import com.wanted.ecommerce.review.domain.Review;
import com.wanted.ecommerce.review.dto.response.RatingResponse;
import com.wanted.ecommerce.review.repository.ReviewRepository;
import com.wanted.ecommerce.seller.domain.Seller;
import com.wanted.ecommerce.seller.dto.response.SellerDetailResponse;
import com.wanted.ecommerce.seller.dto.response.SellerResponse;
import com.wanted.ecommerce.seller.repository.SellerRepository;
import com.wanted.ecommerce.tag.domain.Tag;
import com.wanted.ecommerce.tag.dto.response.TagResponse;
import com.wanted.ecommerce.tag.repository.TagRepository;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ReviewRepository reviewRepository;

    private static ProductDetailImageResponse apply(ProductImage image) {
        return ProductDetailImageResponse.of(image.getId(), image.getUrl(),
            image.getAltText(), image.isPrimary(), image.getDisplayOrder(),
            image.getOption()
                .getId());
    }

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

    @Transactional(readOnly = true)
    @Override
    public Page<ProductListResponse> readAll(ProductReadAllRequest request) {
        int pageNumber = Math.max(0, request.getPage() - 1);
        Pageable pageable = PageRequest.of(pageNumber, request.getPerPage());
        Page<Product> products = productRepository.findAllByRequest(request, pageable);
        return products.map(product -> {
            ProductPrice price = productPriceRepository.findByProductId(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));

            ProductImageResponse primaryImageResponse = productImageRepository
                .findByProductIdAndPrimaryTrue(product.getId())
                .map(image -> ProductImageResponse.of(image.getUrl(), image.getAltText()))
                .orElse(null);

            double avgRating = Optional.ofNullable(
                    reviewRepository.findAvgRatingByProductId(product.getId()))
                .orElse(0.0);

            double rating = Double.parseDouble(String.format("%.2f", avgRating)) ;

            int reviewCount = Optional.ofNullable(
                    reviewRepository.findReviewCountByProductId(product.getId()))
                .orElse(0L)
                .intValue();

            Boolean inStock = productOptionRepository.existsByOptionGroupProductIdAndStockGreaterThan(
                product.getId(), 0);

            BrandResponse brandResponse = BrandResponse.of(product.getBrand().getId(),
                product.getBrand().getName());
            SellerResponse sellerResponse = SellerResponse.of(product.getSeller().getId(),
                product.getSeller().getName());
            return ProductListResponse.of(product.getId(), product.getName(),
                product.getShortDescription(), price.getBasePrice(), price.getSalePrice(),
                price.getCurrency(), primaryImageResponse, brandResponse, sellerResponse, rating,
                reviewCount, inStock, product.getStatus().getName(), product.getCreatedAt());
        });
    }

    @Override
    public ProductDetailResponse readDetail(long productId) {
        // product
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));

        // brand
        Brand brand = product.getBrand();
        BrandDetailResponse brandDetailResponse = BrandDetailResponse.of(brand.getId(),
            brand.getName(), brand.getDescription(), brand.getLogoUrl(), brand.getWebsite());

        // seller
        Seller seller = product.getSeller();
        double sellerRating = seller.getRating()
            .setScale(1, RoundingMode.HALF_UP)
            .doubleValue();
        SellerDetailResponse sellerDetailResponse = SellerDetailResponse.of(seller.getId(),
            seller.getName(), seller.getDescription(), seller.getLogoUrl(), sellerRating,
            seller.getContactEmail(), seller.getContactPhone());

        // product detail
        ProductDetail detail = product.getDetail();
        double weight = detail.getWeight()
            .setScale(1, RoundingMode.HALF_UP)
            .doubleValue();
        DimensionsResponse dimensionsResponse = DimensionsResponse.of(
            detail.getDimensions().getWidth(), detail.getDimensions().getHeight(),
            detail.getDimensions().getDepth());

        Map<String, Object> additionalInfoResponse = detail.getAdditionalInfo();

        DetailResponse detailResponse = DetailResponse.of(weight, dimensionsResponse,
            detail.getMaterials(), detail.getCountryOfOrigin(), detail.getWarrantyInfo(),
            detail.getCareInstructions(), additionalInfoResponse);

        // price
        ProductPrice price = product.getPrice();
        double basePrice = Double.parseDouble(String.format("%.2f", price.getBasePrice()));
        double salePrice = Double.parseDouble(String.format("%.2f", price.getSalePrice()));
        double discount = basePrice - salePrice;
        double discountPercentage = Double.parseDouble(String.format("%.2f", (discount / basePrice) * 100));
        ProductPriceResponse priceResponse = ProductPriceResponse.of(basePrice,
            salePrice, price.getCurrency(), price.getTaxRate().doubleValue(), discountPercentage);

        // categories
        List<ProductCategory> productCategories = product.getCategories();
        List<CategoryResponse> categoryResponses = productCategories.stream().map(productCategory ->
        {
            Category category = productCategory.getCategory();
            Category parent = category.getParent();
            ParentCategoryResponse parentResponse = ParentCategoryResponse.of(parent.getId(),
                parent.getName(), parent.getSlug());
            return CategoryResponse.of(category.getId(), category.getName(), category.getSlug(),
                productCategory.isPrimary(), parentResponse);
        }).toList();

        //option groups
        List<ProductOptionGroupResponse> optionGroupResponses = product.getOptionGroups().stream()
            .map(optionGroup -> {
                List<ProductOptionResponse> options = optionGroup.getOptions().stream()
                    .map(option -> ProductOptionResponse.of(
                        option.getId(), option.getName(), option.getAdditionalPrice().doubleValue(),
                        option.getSku(), option.getStock(), option.getDisplayOrder()
                    ))
                    .toList();

                return ProductOptionGroupResponse.of(optionGroup.getId(), optionGroup.getName(),
                    optionGroup.getDisplayOrder(), options);
            })
            .toList();

        // images
        List<ProductDetailImageResponse> imageResponses = product.getImages().stream()
            .map(ProductServiceImpl::apply).toList();

        // tags
        List<TagResponse> tagResponses = product.getTags().stream().map(productTag ->
            TagResponse.of(productTag.getId(), productTag.getTag().getName(),
                product.getSlug())).toList();

        // rating
        double average = reviewRepository.findAvgRatingByProductId(productId);

        List<Review> reviews = reviewRepository.findReviewsByProductId(productId);

        Map<Integer, Long> rating = IntStream.rangeClosed(1, 5)
            .boxed()
            .collect(Collectors.toMap(Function.identity(), i -> 0L));

        rating.putAll(reviews.stream()
            .collect(Collectors.groupingBy(
                Review::getRating,
                Collectors.counting()
            )));

        Map<Integer, Long> sortedRating = rating.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Entry::getKey,
                Entry::getValue,
                (existing, replacement) -> existing,
                LinkedHashMap::new
            ));

        RatingResponse ratingResponse = RatingResponse.of(average, reviews.size(), sortedRating);

        // related products
        ProductCategory primaryCategory = product.getCategories().stream()
            .filter(ProductCategory::isPrimary)
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));

        List<Product> relatedProducts = productRepository.findRelatedProductsByCategoryId(
            primaryCategory.getCategory().getId());

        List<RelatedProductResponse> relatedProductResponses = relatedProducts.stream()
            .map(relatedProduct -> {
                ProductImageResponse imageResponse = relatedProduct.getImages().stream()
                    .filter(ProductImage::isPrimary)
                    .findFirst()
                    .map(image -> ProductImageResponse.of(image.getUrl(), image.getUrl()))
                    .orElse(null);

                ProductPrice productPrice = relatedProduct.getPrice();

                return RelatedProductResponse.of(relatedProduct.getId(), relatedProduct.getName(),
                    relatedProduct.getSlug(), relatedProduct.getShortDescription(), imageResponse,
                    productPrice.getBasePrice(), productPrice.getSalePrice(),
                    productPrice.getCurrency());
            }).toList();

        return ProductDetailResponse.of(productId, product.getName(),
            product.getSlug(), product.getShortDescription(), product.getFullDescription(),
            sellerDetailResponse, brandDetailResponse, product.getStatus().getName(),
            product.getCreatedAt(), product.getUpdatedAt(), detailResponse, priceResponse,
            categoryResponses, optionGroupResponses, imageResponses, tagResponses, ratingResponse,
            relatedProductResponses);
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
        Dimensions dimensions = Dimensions.of(detailRequest.getDimensions().getWidth(),
            detailRequest.getDimensions().hashCode(), detailRequest.getDimensions().getDepth());

        Map<String, Object> additionalInfo = detailRequest.getAdditionalInfo();

        ProductDetail detail = ProductDetail.of(product, detailRequest.getWeight(),
            dimensions, detailRequest.getMaterials(),
            detailRequest.getCountryOfOrigin(), detailRequest.getWarrantyInfo(),
            detailRequest.getCareInstructions(), additionalInfo);
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
        List<Tag> tags = tagIds.stream().map(tagId -> tagRepository.findById(tagId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND))).toList();

        List<ProductTag> savedTagList = tags.stream().map(tag -> {
            ProductTag productTag = ProductTag.of(saved, tag);
            return productTagRepository.save(productTag);
        }).toList();

        return savedTagList.stream().map(ProductTag::getId).toList();
    }
}
