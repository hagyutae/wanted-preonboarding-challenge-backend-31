package investLee.platform.ecommerce.service;

import investLee.platform.ecommerce.domain.ProductStatus;
import investLee.platform.ecommerce.domain.entity.*;
import investLee.platform.ecommerce.dto.request.*;
import investLee.platform.ecommerce.dto.response.MainPageResponse;
import investLee.platform.ecommerce.dto.response.ProductDetailResponse;
import investLee.platform.ecommerce.dto.response.ProductSummaryResponse;
import investLee.platform.ecommerce.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPriceRepository priceRepository;
    private final ProductOptionRepository optionRepository;
    private final ProductOptionGroupRepository optionGroupRepository;
    private final ProductImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ReviewRepository reviewRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;

    public Page<ProductSummaryResponse> searchProducts(ProductSearchConditionRequest conditionDTO) {
        return productRepository.searchProducts(conditionDTO);
    }

    public ProductDetailResponse getProductDetail(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        ProductPriceEntity price = priceRepository.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품 가격 정보를 찾을 수 없습니다."));
        List<ProductOptionGroupEntity> optionGroups = optionGroupRepository.findByProductId(productId);
        List<ProductImageEntity> images = imageRepository.findByProductId(productId);
        List<ProductCategoryEntity> categories = productCategoryRepository.findByProductId(productId);
        Double avgRating = reviewRepository.getAverageRatingByProductId(productId);

        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .status(product.getStatus())

                .price(new ProductDetailResponse.PriceDTO(
                        price.getBasePrice(), price.getSalePrice(), price.getCostPrice(),
                        price.getCurrency(), price.getTaxRate()))

                .brand(new ProductDetailResponse.BrandDTO(
                        product.getBrand().getId(), product.getBrand().getName(), product.getBrand().getLogoUrl()))

                .seller(new ProductDetailResponse.SellerDTO(
                        product.getSeller().getId(), product.getSeller().getName(), product.getSeller().getRating()))

                .categories(categories.stream()
                        .map(pc -> new ProductDetailResponse.CategoryDTO(
                                pc.getCategory().getId(),
                                pc.getCategory().getName(),
                                pc.getCategory().getLevel()))
                        .toList())

                .images(images.stream()
                        .map(img -> new ProductDetailResponse.ImageDTO(
                                img.getUrl(), img.isPrimary(), img.getAltText()))
                        .toList())

                .optionGroups(optionGroups.stream()
                        .map(g -> new ProductDetailResponse.OptionGroupDTO(
                                g.getName(),
                                g.getOptions().stream()
                                        .map(opt -> new ProductDetailResponse.OptionDTO(
                                                opt.getName(),
                                                opt.getAdditionalPrice(),
                                                opt.getStock(),
                                                opt.getSku()))
                                        .toList()))
                        .toList())

                .averageRating(avgRating)
                .build();
    }

    @Transactional
    public Long createProduct(ProductCreateRequest dto) {
        SellerEntity seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("판매자 없음"));
        BrandEntity brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("브랜드 없음"));

        // 1. 상품 생성
        ProductEntity product = ProductEntity.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .status(dto.getStatus())
                .seller(seller)
                .brand(brand)
                .build();

        productRepository.save(product);

        // 2. 가격 저장
        ProductPriceEntity price = ProductPriceEntity.builder()
                .product(product)
                .basePrice(dto.getPrice().getBasePrice())
                .salePrice(dto.getPrice().getSalePrice())
                .costPrice(dto.getPrice().getCostPrice())
                .currency(dto.getPrice().getCurrency())
                .taxRate(dto.getPrice().getTaxRate())
                .build();

        priceRepository.save(price);

        // 3. 카테고리 매핑
        if (dto.getCategoryIds() != null) {
            for (Long categoryId : dto.getCategoryIds()) {
                CategoryEntity category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new EntityNotFoundException("카테고리 없음"));
                ProductCategoryEntity pc = ProductCategoryEntity.builder()
                        .product(product)
                        .category(category)
                        .isPrimary(categoryId.equals(dto.getPrimaryCategoryId()))
                        .build();
                productCategoryRepository.save(pc);
            }
        }

        // 4. 옵션 그룹 및 옵션
        if (dto.getOptionGroups() != null) {
            for (ProductCreateRequest.ProductOptionGroupDTO groupDTO : dto.getOptionGroups()) {
                ProductOptionGroupEntity group = ProductOptionGroupEntity.builder()
                        .product(product)
                        .name(groupDTO.getName())
                        .displayOrder(groupDTO.getDisplayOrder())
                        .build();

                optionGroupRepository.save(group);

                for (ProductCreateRequest.ProductOptionGroupDTO.ProductOptionDTO optDTO : groupDTO.getOptions()) {
                    ProductOptionEntity opt = ProductOptionEntity.builder()
                            .optionGroup(group)
                            .name(optDTO.getName())
                            .additionalPrice(optDTO.getAdditionalPrice())
                            .sku(optDTO.getSku())
                            .stock(optDTO.getStock())
                            .displayOrder(optDTO.getDisplayOrder())
                            .build();

                    optionRepository.save(opt);
                }
            }
        }

        // 5. 이미지 저장
        if (dto.getImages() != null) {
            for (ProductCreateRequest.ProductImageDTO imgDTO : dto.getImages()) {
                ProductOptionEntity option = null;
                if (imgDTO.getOptionId() != null) {
                    option = optionRepository.findById(imgDTO.getOptionId())
                            .orElseThrow(() -> new EntityNotFoundException("옵션 ID 오류"));
                }

                ProductImageEntity image = ProductImageEntity.builder()
                        .product(product)
                        .url(imgDTO.getUrl())
                        .altText(imgDTO.getAltText())
                        .isPrimary(imgDTO.isPrimary())
                        .displayOrder(imgDTO.getDisplayOrder())
                        .option(option)
                        .build();

                imageRepository.save(image);
            }
        }

        return product.getId();
    }

    public Page<ProductSummaryResponse> searchByKeyword(ProductSearchRequest dto) {
        return productRepository.searchByKeyword(dto);
    }

    public MainPageResponse getMainPageProducts() {
        // 신규 상품: 최신 등록순 상위 10개
        List<ProductSummaryResponse> newProducts = productRepository.findTop10ByOrderByCreatedAtDesc()
                .stream().map(this::mapToSummaryDTO).toList();

        // 인기 상품 (카테고리별 상위 3개): 예시 기준 "조회수 기준"
        Map<String, List<ProductSummaryResponse>> popularProducts = new LinkedHashMap<>();

        List<CategoryEntity> topCategories = categoryRepository.findByLevel(1);
        for (CategoryEntity category : topCategories) {
            List<ProductEntity> products = productRepository.findTop3ByCategory(category.getId());
            List<ProductSummaryResponse> dtos = products.stream()
                    .map(this::mapToSummaryDTO)
                    .toList();
            popularProducts.put(category.getName(), dtos);
        }

        return MainPageResponse.builder()
                .newProducts(newProducts)
                .popularProductsByCategory(popularProducts)
                .build();
    }

    private ProductSummaryResponse mapToSummaryDTO(ProductEntity product) {
        // 가격 정보
        ProductPriceEntity price = priceRepository.findByProductId(product.getId())
                .orElseThrow(() -> new EntityNotFoundException("가격 정보 없음"));

        // 대표 이미지
        String thumbnailUrl = imageRepository.findFirstByProductIdAndIsPrimaryTrueOrderByDisplayOrderAsc(product.getId())
                .map(ProductImageEntity::getUrl)
                .orElse(null);

        // 평점 정보
        Double averageRating = reviewRepository.getAverageRatingByProductId(product.getId());

        return ProductSummaryResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .basePrice(price.getBasePrice())
                .salePrice(price.getSalePrice())
                .thumbnailUrl(thumbnailUrl)
                .brandName(product.getBrand().getName())
                .averageRating(averageRating)
                .build();
    }

    @Transactional
    public void updateProduct(Long productId, ProductUpdateRequest dto) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품 없음"));

        SellerEntity seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("판매자 없음"));
        BrandEntity brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("브랜드 없음"));

        // 1. 기본 정보 업데이트
        product.updateBasicInfo(dto.getName(), dto.getSlug(), dto.getShortDescription(),
                dto.getFullDescription(), dto.getStatus(), seller, brand);

        // 2. 가격 정보 수정
        ProductPriceEntity price = priceRepository.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("가격 정보 없음"));

        price.updatePrice(dto.getPrice());

        // 3. 카테고리 재설정
        productCategoryRepository.deleteByProductId(productId); // 기존 매핑 제거

        if (dto.getCategoryIds() != null) {
            for (Long categoryId : dto.getCategoryIds()) {
                CategoryEntity category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new EntityNotFoundException("카테고리 없음"));

                ProductCategoryEntity pc = ProductCategoryEntity.builder()
                        .product(product)
                        .category(category)
                        .isPrimary(categoryId.equals(dto.getPrimaryCategoryId()))
                        .build();

                productCategoryRepository.save(pc);
            }
        }
    }

    @Transactional
    public void updateProductStatus(Long productId, ProductStatusUpdateRequest dto) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        product.changeStatus(dto.getStatus());
    }

    public List<ProductSummaryResponse> getRelatedProducts(Long productId) {
        return productRepository.findRelatedProducts(productId, 5);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품 없음"));

        product.changeStatus(ProductStatus.DELETED);
    }
}