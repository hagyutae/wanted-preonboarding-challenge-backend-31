package com.ecommerce.product.application;

import com.ecommerce.brand.domain.Brand;
import com.ecommerce.brand.infrastructure.BrandRepository;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.response.ErrorCode;
import com.ecommerce.product.application.dto.req.ProductCreateRequest;
import com.ecommerce.product.application.dto.req.ProductSearchRequest;
import com.ecommerce.product.application.dto.res.ProductCreatedResponse;
import com.ecommerce.product.application.dto.res.ProductResponse;
import com.ecommerce.product.domain.AdditionalInfoVO;
import com.ecommerce.product.domain.Category;
import com.ecommerce.product.domain.DimensionsVO;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductCategory;
import com.ecommerce.product.domain.ProductDetail;
import com.ecommerce.product.domain.ProductImage;
import com.ecommerce.product.domain.ProductOption;
import com.ecommerce.product.domain.ProductOptionGroup;
import com.ecommerce.product.domain.ProductPrice;
import com.ecommerce.product.domain.ProductTag;
import com.ecommerce.product.domain.enumerates.ProductStatus;
import com.ecommerce.product.infrastructure.CategoryRepository;
import com.ecommerce.product.infrastructure.ProductCategoryRepository;
import com.ecommerce.product.infrastructure.ProductDetailRepository;
import com.ecommerce.product.infrastructure.ProductImageRepository;
import com.ecommerce.product.infrastructure.ProductOptionGroupRepository;
import com.ecommerce.product.infrastructure.ProductOptionRepository;
import com.ecommerce.product.infrastructure.ProductPriceRepository;
import com.ecommerce.product.infrastructure.ProductRepository;
import com.ecommerce.product.infrastructure.ProductTagRepository;
import com.ecommerce.review.infrastructure.ReviewRepository;
import com.ecommerce.seller.domain.Seller;
import com.ecommerce.seller.infrastructure.SellerRepository;
import com.ecommerce.tag.domain.Tag;
import com.ecommerce.tag.infrastructure.TagRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductTagRepository productTagRepository;
    private final TagRepository tagRepository;
    private final ProductPriceRepository productPriceRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ReviewRepository reviewRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductCreatedResponse create(ProductCreateRequest request) {
        // 1. 기본 상품 정보 생성
        Seller seller = getSellerById(request.sellerId());
        Brand brand = getBrandById(request.brandId());

        Product product = Product.builder().name(request.name()).slug(request.slug()).shortDescription(request.shortDescription()).fullDescription(request.fullDescription()).seller(seller).brand(brand).status(ProductStatus.valueOf(request.status())).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();

        Product savedProduct = productRepository.save(product);

        // 2. 상품 상세 정보 저장
        saveProductDetail(savedProduct, request.detail());

        // 3. 상품 가격 정보 저장
        saveProductPrice(savedProduct, request.price());

        // 4. 카테고리 매핑 저장
        saveProductCategories(savedProduct, request.categories());

        // 5. 옵션 그룹 및 옵션 저장
        saveProductOptions(savedProduct, request.optionGroups());

        // 6. 이미지 정보 저장 (옵션 ID 매핑 처리 포함)
        saveProductImages(savedProduct, request.images());

        // 7. 태그 매핑 저장
        List<Tag> foundTags = getTags(request.tagIds());
        foundTags.forEach(tag -> {
            saveProductTag(savedProduct, tag);
        });

        return new ProductCreatedResponse(savedProduct.getId(), savedProduct.getName(), savedProduct.getSlug(), savedProduct.getCreatedAt(), savedProduct.getUpdatedAt());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findProducts(ProductSearchRequest request) {
        int pageNumber = Math.max(0, request.getPage() - 1);
        Pageable pageable = PageRequest.of(pageNumber, request.getPerPage());
        Page<Product> products = productRepository.findBySearchRequest(request, pageable);

        return products.map(product -> {
            // 상품 가격 정보 조회
            ProductPrice price = productPriceRepository.findByProductId(product.getId())
                    .orElseThrow(() -> {
                        throw new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND.getMessage());
                    });

            // 상품 기본 이미지 조회
            ProductImage primaryImage = productImageRepository.findByProductIdAndIsPrimaryTrue(product.getId()).orElse(null);

            // 상품 평점과 리뷰 수 조회
            Double avgRating = reviewRepository.getAverageRatingByProductId(product.getId());
            Long reviewCountLong = reviewRepository.getReviewCountByProductId(product.getId());

            Double rating = (avgRating != null) ? avgRating : 0.0;
            Integer reviewCount = (reviewCountLong != null) ? reviewCountLong.intValue() : 0;

            // 재고 여부 확인
            Boolean inStock = productOptionRepository.existsByOptionGroupProductIdAndStockGreaterThan(product.getId(), 0);

            // DTO 반환
            return ProductResponse.from(product, price, primaryImage, rating, reviewCount, inStock);
        });
    }

    private Brand getBrandById(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND.getMessage());
        });
        return brand;
    }

    private Seller getSellerById(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND.getMessage());
        });
        return seller;
    }

    private List<Tag> getTags(List<Long> ids) {
        List<Tag> foundTags = tagRepository.findByIds(ids);
        return foundTags;
    }

    private Long saveProductDetail(Product savedProduct, ProductCreateRequest.ProductDetailRequest detailRequest) {
        ProductDetail savedProductDetail = productDetailRepository.save(ProductDetail.builder()
                .product(savedProduct)
                .weight(detailRequest.weight())
                .materials(detailRequest.materials())
                .dimensions(DimensionsVO.toDimensionsVO(detailRequest.dimensions()))
                .countryOfOrigin(detailRequest.countryOfOrigin())
                .warrantyInfo(detailRequest.warrantyInfo())
                .careInstructions(detailRequest.careInstructions())
                .additionalInfo(AdditionalInfoVO.toAdditionalInfoVO(detailRequest.additionalInfoRequest()))
                .build());

        return savedProductDetail.getId();
    }

    private Long saveProductTag(Product savedProduct, Tag tag) {
        ProductTag savedProductTag = productTagRepository.save(ProductTag.builder().product(savedProduct).tag(tag).build());

        return savedProductTag.getId();
    }

    private List<Long> saveProductImages(Product savedProduct, List<ProductCreateRequest.ProductImageRequest> images) {
        List<Long> savedImageIds = new ArrayList<>();

        images.forEach(image -> {
            ProductOption option = null;
            if (image.optionId() != null) {
                option = productOptionRepository.findById(image.optionId()).orElse(null);
            }

            ProductImage savedImage = productImageRepository.save(
                    ProductImage.builder()
                            .product(savedProduct)
                            .url(image.url())
                            .altText(image.altText())
                            .isPrimary(image.isPrimary())
                            .displayOrder(image.displayOrder())
                            .option(option)
                            .build());

            savedImageIds.add(savedImage.getId());
        });

        return savedImageIds;
    }


    private List<Long> saveProductOptions(Product savedProduct, List<ProductCreateRequest.ProductOptionGroupRequest> optionGroupRequests) {
        List<Long> savedOptionIds = new ArrayList<>();

        if (optionGroupRequests == null || optionGroupRequests.isEmpty()) {
            return savedOptionIds;
        }

        // 각 옵션 처리
        optionGroupRequests.forEach(groupRequest -> {
            ProductOptionGroup savedOptionGroup = productOptionGroupRepository.save(ProductOptionGroup.builder().product(savedProduct).name(groupRequest.name()).displayOrder(groupRequest.displayOrder()).build());
            groupRequest.options().stream().map(optionRequest -> ProductOption.builder().optionGroup(savedOptionGroup).name(optionRequest.name()).additionalPrice(optionRequest.additionalPrice()).sku(optionRequest.sku()).stock(optionRequest.stock()).displayOrder(optionRequest.displayOrder()).build()).map(productOptionRepository::save).map(ProductOption::getId).forEach(savedOptionIds::add);
        });

        return savedOptionIds;
    }

    private List<ProductCategory> saveProductCategories(Product savedProduct, List<ProductCreateRequest.ProductCategoryRequest> categories) {
        ArrayList<ProductCategory> savedCategories = new ArrayList<>();
        categories.forEach(category -> {
            Category foundCategory = categoryRepository.findById(category.categoryId()).orElseThrow(() -> {
                throw new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND.getMessage());
            });
            ProductCategory savedProductCategory = productCategoryRepository.save(ProductCategory.builder()
                    .product(savedProduct)
                    .category(foundCategory)
                    .isPrimary(category.isPrimary())
                    .build());
            savedCategories.add(savedProductCategory);
        });

        return savedCategories;
    }

    private Long saveProductPrice(Product savedProduct, ProductCreateRequest.ProductPriceRequest price) {
        ProductPrice productPrice = productPriceRepository.save(ProductPrice.builder().product(savedProduct).basePrice(price.basePrice()).salePrice(price.salePrice()).costPrice(price.costPrice()).currency(price.currency()).taxRate(price.taxRate()).build());

        return productPrice.getId();
    }
}
