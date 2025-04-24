package com.ecommerce.product.application;

import com.ecommerce.brand.domain.Brand;
import com.ecommerce.brand.infrastructure.BrandRepository;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.response.ErrorCode;
import com.ecommerce.product.application.dto.req.ProductCreateRequest;
import com.ecommerce.product.application.dto.res.ProductCreatedResponse;
import com.ecommerce.product.domain.AdditionalInfoVO;
import com.ecommerce.product.domain.DimensionsVO;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductDetail;
import com.ecommerce.product.domain.ProductImage;
import com.ecommerce.product.domain.ProductOption;
import com.ecommerce.product.domain.ProductOptionGroup;
import com.ecommerce.product.domain.ProductPrice;
import com.ecommerce.product.domain.ProductTag;
import com.ecommerce.product.domain.enumerates.ProductStatus;
import com.ecommerce.product.infrastructure.ProductDetailRepository;
import com.ecommerce.product.infrastructure.ProductImageRepository;
import com.ecommerce.product.infrastructure.ProductOptionGroupRepository;
import com.ecommerce.product.infrastructure.ProductOptionRepository;
import com.ecommerce.product.infrastructure.ProductPriceRepository;
import com.ecommerce.product.infrastructure.ProductRepository;
import com.ecommerce.product.infrastructure.ProductTagRepository;
import com.ecommerce.seller.domain.Seller;
import com.ecommerce.seller.infrastructure.SellerRepository;
import com.ecommerce.tag.domain.Tag;
import com.ecommerce.tag.infrastructure.TagRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public ProductCreatedResponse create(ProductCreateRequest request) {
        // 1. 기본 상품 정보 생성
        Seller seller = sellerRepository.findById(request.sellerId())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND.getMessage());
                });
        Brand brand = brandRepository.findById(request.brandId()).orElseThrow(() -> {
            throw new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND.getMessage());
        });

        Product product = Product.builder()
                .name(request.name())
                .slug(request.slug())
                .shortDescription(request.shortDescription())
                .fullDescription(request.fullDescription())
                .seller(seller)
                .brand(brand)
                .status(ProductStatus.valueOf(request.status()))
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();

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
        List<Tag> foundTags = getTags(request);
        foundTags.forEach(tag -> {
            saveProductTag(savedProduct, tag);
        });

        return new ProductCreatedResponse(savedProduct.getId(), savedProduct.getName(), savedProduct.getSlug(), savedProduct.getCreatedAt(), savedProduct.getUpdatedAt());
    }

    private List<Tag> getTags(ProductCreateRequest request) {
        List<Tag> foundTags = tagRepository.findByIds(request.tagIds());
        return foundTags;
    }

    private Long saveProductDetail(Product savedProduct, ProductCreateRequest.ProductDetailRequest detailRequest) {
        ProductDetail savedProductDetail;
        savedProductDetail = productDetailRepository.save(
                ProductDetail.builder()
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
                option = productOptionRepository.findById(image.optionId())
                        .orElse(null);
            }

            ProductImage savedImage = productImageRepository.save(ProductImage.builder()
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

        // 옵션 그룹이 없는 경우 빈 리스트 반환
        if (optionGroupRequests == null || optionGroupRequests.isEmpty()) {
            return savedOptionIds;
        }

        // 각 옵션 그룹 처리
        for (ProductCreateRequest.ProductOptionGroupRequest groupRequest : optionGroupRequests) {
            // 옵션 그룹 생성 및 저장
            ProductOptionGroup optionGroup = ProductOptionGroup.builder()
                    .product(savedProduct)
                    .name(groupRequest.name())
                    .displayOrder(groupRequest.displayOrder())
                    .build();

            ProductOptionGroup savedOptionGroup = productOptionGroupRepository.save(optionGroup);

            // 각 옵션 처리
            for (ProductCreateRequest.ProductOptionGroupRequest.ProductOptionRequest optionRequest : groupRequest.options()) {
                ProductOption option = ProductOption.builder()
                        .optionGroup(savedOptionGroup)
                        .name(optionRequest.name())
                        .additionalPrice(optionRequest.additionalPrice())
                        .sku(optionRequest.sku())
                        .stock(optionRequest.stock())
                        .displayOrder(optionRequest.displayOrder())
                        .build();

                ProductOption savedOption = productOptionRepository.save(option);
                savedOptionIds.add(savedOption.getId());
            }
        }

        return savedOptionIds;
    }

    private Long saveProductCategories(Product savedProduct, List<ProductCreateRequest.ProductCategoryRequest> categories) {
        return null;
    }

    private Long saveProductPrice(Product savedProduct, ProductCreateRequest.ProductPriceRequest price) {
        ProductPrice productPrice = productPriceRepository.save(ProductPrice.builder()
                .product(savedProduct)
                .basePrice(price.basePrice())
                .salePrice(price.salePrice())
                .costPrice(price.costPrice())
                .currency(price.currency())
                .taxRate(price.taxRate())
                .build());

        return productPrice.getId();
    }
}
