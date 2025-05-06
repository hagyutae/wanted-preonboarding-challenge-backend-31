package minseok.cqrschallenge.product.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.brand.entity.Brand;
import minseok.cqrschallenge.brand.repository.BrandRepository;
import minseok.cqrschallenge.category.repository.CategoryRepository;
import minseok.cqrschallenge.common.exception.ResourceNotFoundException;
import minseok.cqrschallenge.product.dto.request.ProductCreateRequest;
import minseok.cqrschallenge.product.dto.response.ProductCreateResponse;
import minseok.cqrschallenge.product.dto.response.ProductDetailResponse;
import minseok.cqrschallenge.product.dto.response.ProductListResponse;
import minseok.cqrschallenge.product.dto.response.ProductUpdateResponse;
import minseok.cqrschallenge.product.entity.Dimensions;
import minseok.cqrschallenge.product.entity.Product;
import minseok.cqrschallenge.product.entity.ProductCategory;
import minseok.cqrschallenge.product.entity.ProductDetail;
import minseok.cqrschallenge.product.entity.ProductImage;
import minseok.cqrschallenge.product.entity.ProductOption;
import minseok.cqrschallenge.product.entity.ProductOptionGroup;
import minseok.cqrschallenge.product.entity.ProductPrice;
import minseok.cqrschallenge.product.entity.ProductStatus;
import minseok.cqrschallenge.product.entity.ProductTag;
import minseok.cqrschallenge.product.repository.ProductOptionRepository;
import minseok.cqrschallenge.review.repository.ReviewRepository;
import minseok.cqrschallenge.seller.entity.Seller;
import minseok.cqrschallenge.seller.repository.SellerRepository;
import minseok.cqrschallenge.tag.entity.Tag;
import minseok.cqrschallenge.tag.repository.TagRepository;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ReviewRepository reviewRepository;
    private final BrandRepository brandRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ProductOptionRepository productOptionRepository;

    public Product toEntity(ProductCreateRequest request) {
        Product product = Product.builder()
            .name(request.getName())
            .slug(request.getSlug())
            .shortDescription(request.getShortDescription())
            .fullDescription(request.getFullDescription())
            .status(ProductStatus.valueOf(request.getStatus()))
            .build();

        mapSeller(product, request);
        mapBrand(product, request);
        mapPrice(product, request);
        mapDetail(product, request);
        mapCategories(product, request);
        mapOptionGroups(product, request);
        mapTags(product, request);

        return product;
    }

    public void mapImages(Product product, ProductCreateRequest request) {
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            request.getImages().forEach(imageRequest -> {
                ProductImage.ProductImageBuilder imageBuilder = ProductImage.builder()
                    .url(imageRequest.getUrl())
                    .altText(imageRequest.getAltText())
                    .displayOrder(imageRequest.getDisplayOrder())
                    .isPrimary(imageRequest.getIsPrimary());

                if (imageRequest.getOptionId() != null) {
                    ProductOption option = productOptionRepository.findById(
                            imageRequest.getOptionId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                            "옵션을 찾을 수 없습니다: " + imageRequest.getOptionId()));
                    imageBuilder.option(option);
                }

                ProductImage image = imageBuilder.build();
                product.addImage(image);
            });
        }
    }

    public ProductListResponse toListResponse(Product product) {
        return ProductListResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .slug(product.getSlug())
            .seller(buildSellerSummary(product))
            .brand(buildBrandSummary(product))
            .shortDescription(product.getShortDescription())
            .basePrice(product.getPrice().getBasePrice())
            .salePrice(product.getPrice().getSalePrice())
            .currency(product.getPrice().getCurrency())
            .status(product.getStatus().name())
            .rating(getRating(product))
            .reviewCount(getReviewCount(product))
            .createdAt(product.getCreatedAt())
            .build();
    }

    public ProductDetailResponse toDetailResponse(Product product) {
        return ProductDetailResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .slug(product.getSlug())
            .shortDescription(product.getShortDescription())
            .fullDescription(product.getFullDescription())
            .seller(buildSellerDetail(product))
            .brand(buildBrandDetail(product))
            .price(buildPriceInfo(product))
            .categories(buildCategoryInfo(product))
            .optionGroups(buildOptionGroups(product))
            .images(buildImages(product))
            .tags(buildTags(product))
            .detail(buildProductDetailInfo(product))
            .rating(buildRatingInfo(product))
            .status(product.getStatus().name())
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getUpdatedAt())
            .build();
    }


    public ProductCreateResponse toCreateResponse(Product product) {
        return ProductCreateResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .slug(product.getSlug())
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getUpdatedAt())
            .build();
    }


    public ProductUpdateResponse toUpdateResponse(Product product) {
        return ProductUpdateResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .slug(product.getSlug())
            .updatedAt(product.getUpdatedAt())
            .build();
    }

    private void mapSeller(Product product, ProductCreateRequest request) {
        if (request.getSellerId() != null) {
            Seller seller = sellerRepository.findById(request.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("판매자를 찾을 수 없습니다."));
            product.assignSeller(seller);
        }
    }

    private void mapBrand(Product product, ProductCreateRequest request) {
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("브랜드를 찾을 수 없습니다."));
            product.assignBrand(brand);
        }
    }

    private void mapPrice(Product product, ProductCreateRequest request) {
        if (request.getPrice() != null) {
            product.addPrice(
                ProductPrice.builder()
                    .basePrice(request.getPrice().getBasePrice())
                    .salePrice(request.getPrice().getSalePrice())
                    .costPrice(request.getPrice().getCostPrice())
                    .currency(request.getPrice().getCurrency())
                    .taxRate(request.getPrice().getTaxRate())
                    .build()
            );
        }
    }

    private void mapDetail(Product product, ProductCreateRequest request) {
        if (request.getDetail() != null) {
            product.addDetail(ProductDetail.builder()
                .product(product)
                .weight(request.getDetail().getWeight())
                .dimensions(request.getDetail().getDimensions())
                .materials(request.getDetail().getMaterials())
                .countryOfOrigin(request.getDetail().getCountryOfOrigin())
                .warrantyInfo(request.getDetail().getWarrantyInfo())
                .careInstructions(request.getDetail().getCareInstructions())
                .additionalInfo(request.getDetail().getAdditionalInfo())
                .build());
        }
    }

    private void mapCategories(Product product, ProductCreateRequest request) {
        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            request.getCategories().forEach(categoryRequest ->
                product.addCategory(ProductCategory.builder()
                    .category(categoryRepository.findById(categoryRequest.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("카테고리를 찾을 수 없습니다.")))
                    .isPrimary(categoryRequest.getIsPrimary())
                    .build()));
        }
    }

    private void mapOptionGroups(Product product, ProductCreateRequest request) {
        if (request.getOptionGroups() != null && !request.getOptionGroups().isEmpty()) {
            request.getOptionGroups().forEach(optionGroupRequest -> {
                ProductOptionGroup optionGroup = ProductOptionGroup.builder()
                    .name(optionGroupRequest.getName())
                    .displayOrder(optionGroupRequest.getDisplayOrder())
                    .build();
                product.addOptionGroup(optionGroup);

                optionGroupRequest.getOptions().forEach(optionRequest -> {
                    ProductOption option = ProductOption.builder()
                        .name(optionRequest.getName())
                        .additionalPrice(optionRequest.getAdditionalPrice())
                        .sku(optionRequest.getSku())
                        .stock(optionRequest.getStock())
                        .displayOrder(optionRequest.getDisplayOrder())
                        .build();
                    optionGroup.addOption(option);
                });
            });
        }
    }

    private void mapTags(Product product, ProductCreateRequest request) {
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            request.getTags().forEach(tagId -> {
                Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new ResourceNotFoundException("태그를 찾을 수 없습니다."));
                ProductTag productTag = ProductTag.builder()
                    .tag(tag)
                    .build();
                product.addTag(productTag);
            });
        }
    }

    private double getRating(Product product) {
        Long productId = product.getId();
        Integer reviewCount = reviewRepository.countByProductId(productId);

        if (reviewCount == 0) {
            return 0.0;
        } else {
            return reviewRepository.calculateAverageRating(productId);
        }
    }

    private int getReviewCount(Product product) {
        return reviewRepository.countByProductId(product.getId());
    }

    private ProductListResponse.BrandSummary buildBrandSummary(Product product) {
        if (product.getBrand() == null) {
            return null;
        }

        return ProductListResponse.BrandSummary.builder()
            .id(product.getBrand().getId())
            .name(product.getBrand().getName())
            .build();
    }

    private ProductListResponse.SellerSummary buildSellerSummary(Product product) {
        if (product.getSeller() == null) {
            return null;
        }

        return ProductListResponse.SellerSummary.builder()
            .id(product.getSeller().getId())
            .name(product.getSeller().getName())
            .build();
    }


    private ProductDetailResponse.SellerDetail buildSellerDetail(Product product) {
        if (product.getSeller() == null) {
            return null;
        }

        return ProductDetailResponse.SellerDetail.builder()
            .id(product.getSeller().getId())
            .name(product.getSeller().getName())
            .description(product.getSeller().getDescription())
            .logoUrl(product.getSeller().getLogoUrl())
            .rating(product.getSeller().getRating())
            .contactEmail(product.getSeller().getContactEmail())
            .contactPhone(product.getSeller().getContactPhone())
            .build();
    }

    private ProductDetailResponse.ProductDetailInfo buildProductDetailInfo(Product product) {
        if (product.getDetail() == null) {
            return null;
        }

        return ProductDetailResponse.ProductDetailInfo.builder()
            .dimensions(
                new Dimensions(product.getDetail().getDimensions().getHeight(),
                    product.getDetail().getDimensions().getWidth(),
                    product.getDetail().getDimensions().getHeight()))
            .weight(product.getDetail().getWeight())
            .countryOfOrigin(product.getDetail().getCountryOfOrigin())
            .materials(product.getDetail().getMaterials())
            .warrantyInfo(product.getDetail().getWarrantyInfo())
            .careInstructions(product.getDetail().getCareInstructions())
            .additionalInfo(product.getDetail().getAdditionalInfo())
            .build();
    }

    private ProductDetailResponse.BrandDetail buildBrandDetail(Product product) {
        if (product.getBrand() == null) {
            return null;
        }

        return ProductDetailResponse.BrandDetail.builder()
            .id(product.getBrand().getId())
            .name(product.getBrand().getName())
            .description(product.getBrand().getDescription())
            .logoUrl(product.getBrand().getLogoUrl())
            .website(product.getBrand().getWebsite())
            .build();
    }


    private ProductDetailResponse.ProductPriceInfo buildPriceInfo(Product product) {
        if (product.getPrice() == null) {
            return null;
        }

        BigDecimal basePrice = product.getPrice().getBasePrice();
        BigDecimal salePrice = product.getPrice().getSalePrice();

        Integer discountPercentage = null;
        if (basePrice != null && salePrice != null && basePrice.compareTo(BigDecimal.ZERO) > 0) {
            discountPercentage = basePrice
                .subtract(salePrice)
                .multiply(new BigDecimal("100"))
                .divide(basePrice, 0, RoundingMode.HALF_UP)
                .intValue();
        }

        return ProductDetailResponse.ProductPriceInfo.builder()
            .basePrice(basePrice)
            .salePrice(salePrice)
            .currency(product.getPrice().getCurrency())
            .taxRate(product.getPrice().getTaxRate())
            .discountPercentage(discountPercentage)
            .build();
    }

    private List<ProductDetailResponse.CategoryInfo> buildCategoryInfo(Product product) {
        if (product.getCategories() == null || product.getCategories().isEmpty()) {
            return null;
        }

        return product.getCategories().stream()
            .map(category -> ProductDetailResponse.CategoryInfo.builder()
                .id(category.getId())
                .name(category.getCategory().getName())
                .slug(category.getCategory().getSlug())
                .isPrimary(category.getIsPrimary())
                .parent(category.getCategory().getParent() != null
                    ? ProductDetailResponse.CategoryInfo.CategoryParentInfo.builder()
                    .id(category.getCategory().getParent().getId())
                    .name(category.getCategory().getParent().getName())
                    .slug(category.getCategory().getParent().getSlug())
                    .build()
                    : null)
                .build())
            .collect(Collectors.toList());
    }

    private List<ProductDetailResponse.OptionGroupInfo> buildOptionGroups(Product product) {
        if (product.getOptionGroups() == null || product.getOptionGroups().isEmpty()) {
            return null;
        }

        return product.getOptionGroups().stream()
            .map(og -> ProductDetailResponse.OptionGroupInfo.builder()
                .id(og.getId())
                .name(og.getName())
                .displayOrder(og.getDisplayOrder())
                .options(og.getOptions().stream()
                    .map(opt -> ProductDetailResponse.OptionGroupInfo.OptionInfo.builder()
                        .id(opt.getId())
                        .name(opt.getName())
                        .additionalPrice(opt.getAdditionalPrice())
                        .sku(opt.getSku())
                        .stock(opt.getStock())
                        .displayOrder(opt.getDisplayOrder())
                        .build())
                    .collect(Collectors.toList()))
                .build())
            .collect(Collectors.toList());
    }

    private List<ProductDetailResponse.ProductImageInfo> buildImages(Product product) {
        if (product.getImages() == null || product.getImages().isEmpty()) {
            return null;
        }

        return product.getImages().stream()
            .map(img -> new ProductDetailResponse.ProductImageInfo(
                img.getId(),
                img.getUrl(),
                img.getAltText(),
                img.getIsPrimary(),
                img.getDisplayOrder(),
                img.getOption() != null ? img.getOption().getId() : null))
            .collect(Collectors.toList());
    }

    private List<ProductDetailResponse.TagInfo> buildTags(Product product) {
        if (product.getTags() == null || product.getTags().isEmpty()) {
            return null;
        }

        return product.getTags().stream()
            .map(tag -> new ProductDetailResponse.TagInfo(
                tag.getId(),
                tag.getTag().getName(),
                tag.getTag().getSlug()))
            .collect(Collectors.toList());
    }

    private ProductDetailResponse.RatingInfo buildRatingInfo(Product product) {
        Long productId = product.getId();
        Integer reviewCount = reviewRepository.countByProductId(productId);
        if (reviewCount == 0) {
            return null;
        }

        Double averageRating = reviewRepository.calculateAverageRating(productId);

        List<Object[]> ratingDistribution = reviewRepository.countByRatingForProduct(productId);
        Map<String, Integer> distribution = new HashMap<>();

        for (int i = 1; i <= 5; i++) {
            distribution.put(String.valueOf(i), 0);
        }

        for (Object[] data : ratingDistribution) {
            Integer rating = ((Number) data[0]).intValue();
            Integer count = ((Number) data[1]).intValue();
            distribution.put(String.valueOf(rating), count);
        }

        return ProductDetailResponse.RatingInfo.builder()
            .average(averageRating)
            .count(reviewCount)
            .distribution(distribution)
            .build();
    }
}