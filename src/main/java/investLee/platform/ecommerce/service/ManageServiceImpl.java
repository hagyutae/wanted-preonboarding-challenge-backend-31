package investLee.platform.ecommerce.service;

import investLee.platform.ecommerce.domain.*;
import investLee.platform.ecommerce.domain.entity.*;
import investLee.platform.ecommerce.dto.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageServiceImpl implements ManageService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository detailRepository;
    private final ProductPriceRepository priceRepository;
    private final ProductOptionGroupRepository optionGroupRepository;
    private final ProductOptionRepository optionRepository;
    private final ProductImageRepository imageRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;

    @Transactional
    public Long createProduct(ProductCreateRequest request) {
        SellerEntity seller = sellerRepository.findById(request.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("판매자가 존재하지 않습니다."));
        BrandEntity brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("브랜드가 존재하지 않습니다."));

        ProductEntity product = ProductEntity.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .shortDescription(request.getShortDescription())
                .fullDescription(request.getFullDescription())
                .seller(seller)
                .brand(brand)
                .status(request.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        productRepository.save(product);

        ProductCreateRequest.ProductDetailDto d = request.getDetail();
        detailRepository.save(ProductDetailEntity.builder()
                .product(product)
                .weight(d.getWeight())
                .dimensions(d.getDimensions())
                .materials(d.getMaterials())
                .countryOfOrigin(d.getCountryOfOrigin())
                .warrantyInfo(d.getWarrantyInfo())
                .careInstructions(d.getCareInstructions())
                .additionalInfo(d.getAdditionalInfo())
                .build());

        ProductCreateRequest.ProductPriceDto p = request.getPrice();
        priceRepository.save(ProductPriceEntity.builder()
                .product(product)
                .basePrice(p.getBasePrice())
                .salePrice(p.getSalePrice())
                .costPrice(p.getCostPrice())
                .currency(p.getCurrency())
                .taxRate(p.getTaxRate())
                .build());

        for (ProductCreateRequest.OptionGroupDto og : request.getOptionGroups()) {
            ProductOptionGroupEntity group = ProductOptionGroupEntity.builder()
                    .product(product)
                    .name(og.getName())
                    .displayOrder(og.getDisplayOrder())
                    .build();
            optionGroupRepository.save(group);

            for (ProductCreateRequest.OptionGroupDto.ProductOptionDto o : og.getOptions()) {
                optionRepository.save(ProductOptionEntity.builder()
                        .optionGroup(group)
                        .name(o.getName())
                        .additionalPrice(o.getAdditionalPrice())
                        .sku(o.getSku())
                        .stock(o.getStock())
                        .displayOrder(o.getDisplayOrder())
                        .build());
            }
        }

        for (ProductCreateRequest.ProductImageDto i : request.getImages()) {
            imageRepository.save(ProductImageEntity.builder()
                    .product(product)
                    .url(i.getUrl())
                    .altText(i.getAltText())
                    .isPrimary(i.getIsPrimary())
                    .displayOrder(i.getDisplayOrder())
                    .build());
        }

        return product.getId();
    }

    @Transactional
    @Override
    public void updateProduct(Long productId, ProductUpdateRequest request) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        // 기본 정보 업데이트
        product.updateBasicInfo(
                request.getName(),
                request.getSlug(),
                request.getShortDescription(),
                request.getFullDescription(),
                request.getStatus(),
                LocalDateTime.now()
        );

        // 상세 정보 업데이트
        ProductDetailEntity detail = detailRepository.findByProduct(product)
                .orElseThrow(() -> new IllegalStateException("상품 상세 정보가 없습니다."));
        ProductCreateRequest.ProductDetailDto d = request.getDetail();
        detail.update(d.getWeight(), d.getDimensions(), d.getMaterials(),
                d.getCountryOfOrigin(), d.getWarrantyInfo(), d.getCareInstructions(), d.getAdditionalInfo());

        // 가격 정보 업데이트
        ProductPriceEntity price = priceRepository.findByProduct(product)
                .orElseThrow(() -> new IllegalStateException("상품 가격 정보가 없습니다."));
        ProductCreateRequest.ProductPriceDto p = request.getPrice();
        price.update(
                p.getBasePrice(),
                p.getSalePrice(),
                p.getCostPrice(),
                p.getCurrency(),
                p.getTaxRate()
        );

        // 기존 옵션/그룹 삭제 후 다시 저장
        List<ProductOptionGroupEntity> groups = optionGroupRepository.findByProduct(product);
        for (ProductOptionGroupEntity group : groups) {
            optionRepository.deleteAllByOptionGroup(group);
        }
        optionGroupRepository.deleteAll(groups);

        for (ProductCreateRequest.OptionGroupDto og : request.getOptionGroups()) {
            ProductOptionGroupEntity group = ProductOptionGroupEntity.builder()
                    .product(product)
                    .name(og.getName())
                    .displayOrder(og.getDisplayOrder())
                    .build();
            optionGroupRepository.save(group);

            for (ProductCreateRequest.OptionGroupDto.ProductOptionDto o : og.getOptions()) {
                optionRepository.save(ProductOptionEntity.builder()
                        .optionGroup(group)
                        .name(o.getName())
                        .additionalPrice(o.getAdditionalPrice())
                        .sku(o.getSku())
                        .stock(o.getStock())
                        .displayOrder(o.getDisplayOrder())
                        .build());
            }
        }

        // 기존 이미지 삭제 후 다시 저장
        imageRepository.deleteAllByProduct(product);
        for (ProductCreateRequest.ProductImageDto i : request.getImages()) {
            imageRepository.save(ProductImageEntity.builder()
                    .product(product)
                    .url(i.getUrl())
                    .altText(i.getAltText())
                    .isPrimary(i.getIsPrimary())
                    .displayOrder(i.getDisplayOrder())
                    .build());
        }
    }

    @Transactional
    @Override
    public void deleteProduct(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        product.delete(
                "삭제됨",
                LocalDateTime.now()
        );
    }

    @Transactional
    @Override
    public void addOption(
            Long productId,
            OptionCreateRequest request
    ) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        ProductOptionGroupEntity optionGroup = optionGroupRepository
                .findById(request.getOptionGroupId())
                .orElseThrow(() -> new IllegalArgumentException("옵션 그룹이 존재하지 않습니다."));

        if (!optionGroup.getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("옵션 그룹이 해당 상품과 일치하지 않습니다.");
        }

        ProductOptionEntity option = ProductOptionEntity.builder()
                .optionGroup(optionGroup)
                .name(request.getName())
                .additionalPrice(request.getAdditionalPrice())
                .sku(request.getSku())
                .stock(request.getStock())
                .displayOrder(request.getDisplayOrder())
                .build();

        optionRepository.save(option);
    }

    @Transactional
    @Override
    public void updateOption(
            Long optionId,
            OptionUpdateRequest request
    ) {
        ProductOptionEntity option = optionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("옵션이 존재하지 않습니다."));

        option.update(
                request.getName(),
                request.getAdditionalPrice(),
                request.getSku(),
                request.getStock(),
                request.getDisplayOrder()
        );
    }

    @Transactional
    @Override
    public void deleteOption(Long optionId) {
        ProductOptionEntity option = optionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("옵션이 존재하지 않습니다."));

        optionRepository.delete(option);
    }

    @Transactional
    @Override
    public void addImage(Long productId, ImageCreateRequest request) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        ProductOptionEntity option = null;
        if (request.getOptionId() != null) {
            option = optionRepository.findById(request.getOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("옵션이 존재하지 않습니다."));
        }

        ProductImageEntity image = ProductImageEntity.builder()
                .product(product)
                .url(request.getUrl())
                .altText(request.getAltText())
                .isPrimary(request.getIsPrimary())
                .displayOrder(request.getDisplayOrder())
                .option(option)
                .build();

        imageRepository.save(image);
    }

    @Transactional
    @Override
    public void updateImage(Long imageId, ImageUpdateRequest request) {
        ProductImageEntity image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("이미지가 존재하지 않습니다."));

        ProductOptionEntity option = null;
        if (request.getOptionId() != null) {
            option = optionRepository.findById(request.getOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("옵션이 존재하지 않습니다."));
        }

        image.update(
                request.getUrl(),
                request.getAltText(),
                request.getIsPrimary(),
                request.getDisplayOrder(),
                option
        );
    }

    @Transactional
    @Override
    public void deleteImage(Long imageId) {
        ProductImageEntity image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("이미지가 존재하지 않습니다."));

        imageRepository.delete(image);
    }
}