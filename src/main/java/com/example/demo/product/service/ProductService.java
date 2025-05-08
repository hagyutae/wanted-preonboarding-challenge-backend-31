package com.example.demo.product.service;

import com.example.demo.brand.entity.BrandEntity;
import com.example.demo.brand.repository.BrandRepository;
import com.example.demo.category.dto.CategoryQueryFilter;
import com.example.demo.category.entity.CategoryEntity;
import com.example.demo.category.repository.CategoryRepository;
import com.example.demo.common.exception.ErrorCode;
import com.example.demo.common.exception.GlobalException;
import com.example.demo.product.ProductError;
import com.example.demo.product.controller.request.AddProductImageRequest;
import com.example.demo.product.controller.request.CreateProductRequest;
import com.example.demo.product.controller.request.ProductCategoryRequest;
import com.example.demo.product.controller.request.UpdateProductRequest;
import com.example.demo.product.entity.*;
import com.example.demo.product.dto.*;
import com.example.demo.product.repository.*;
import com.example.demo.productoption.entity.ProductOptionEntity;
import com.example.demo.productoption.entity.ProductOptionGroupEntity;
import com.example.demo.productoption.dto.ProductStock;
import com.example.demo.productoption.repository.ProductOptionGroupRepository;
import com.example.demo.productoption.repository.ProductOptionRepository;
import com.example.demo.review.dto.ReviewDistribution;
import com.example.demo.review.dto.ReviewStatistic;
import com.example.demo.review.service.ReviewService;
import com.example.demo.tag.entity.TagEntity;
import com.example.demo.tag.repository.TagRepository;
import com.example.demo.user.entity.SellerEntity;
import com.example.demo.user.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ReviewService reviewService;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductSummary> findProductSummaryPage(ProductQueryFilter productQueryFilter, Pageable pageable) {
        Page<ProductEntity> productEntityPage = productRepository.findProductSummaryPage(productQueryFilter, pageable);

        List<Long> productIds = productEntityPage.getContent().stream().map(ProductEntity::getId)
                .toList();

        List<ProductImageEntity> productPrimaryImages = productImageRepository.findAllByProductIdsAndIsPrimary(productIds);
        Map<Long, ProductImageEntity> productPrimaryImageMap = productPrimaryImages.stream()
                .collect(Collectors.toMap(
                        image -> image.getProductEntity().getId(),
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
        List<ReviewStatistic> reviewStatistics = reviewService.findStatisticsByProductIds(productIds);
        Map<Long, ReviewStatistic> reviewStatisticMap = reviewStatistics.stream()
                .collect(Collectors.toMap(
                        ReviewStatistic::productId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
        List<ProductStock> productStocks = productOptionRepository.findAllByProductIds(productIds);
        Map<Long, ProductStock> productStockMap = productStocks.stream()
                .collect(Collectors.toMap(
                        ProductStock::productId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        List<ProductSummary> productSummaries = productEntityPage.getContent().stream().map(productEntity ->
                ProductSummary.of(productEntity, productPrimaryImageMap.get(productEntity.getId()), reviewStatisticMap.get(productEntity.getId()), productStockMap.get(productEntity.getId()))
        ).toList();

        return new PageImpl<>(productSummaries, pageable, productEntityPage.getTotalElements());
    }

    public CreateProductResult createProduct(CreateProductRequest request) {
        // product slug 존재 여부 확인
        Boolean alreadyExistSlug = productRepository.existBySlug(request.slug());
        if (Boolean.TRUE.equals(alreadyExistSlug)) {
            throw new GlobalException(ErrorCode.CONFLICT, ProductError.PRODUCT_CREATE_FAIL);
        }
        SellerEntity sellerEntity = sellerRepository.findById(request.sellerId())
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_CREATE_FAIL));
        BrandEntity brandEntity = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_CREATE_FAIL));
        List<Long> categoryIds = request.categories().stream()
                .map(ProductCategoryRequest::categoryId)
                .toList();

        ProductEntity productEntity = ProductEntity.create(request, sellerEntity, brandEntity);
        ProductEntity savedProductEntity = productRepository.save(productEntity);

        // category 존재 여부 확인
        CategoryQueryFilter filter = CategoryQueryFilter.builder()
                .categoryIds(categoryIds)
                .build();
        List<CategoryEntity> categoryEntityList = categoryRepository.findAllByFilter(filter);
        Map<Long, CategoryEntity> categoryEntityMap = categoryEntityList.stream()
                .collect(Collectors.toMap(
                        CategoryEntity::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
        if (categoryEntityList.size() != request.categories().size()) {
            throw new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_CREATE_FAIL);
        }
        // product category 저장
        List<ProductCategoryEntity> productCategoryEntityList = request.categories().stream().map(it ->
                ProductCategoryEntity.create(
                        savedProductEntity,
                        categoryEntityMap.get(it.categoryId()),
                        it.isPrimary()
                )
        ).toList();
        productCategoryRepository.saveAll(productCategoryEntityList);

        // product option 저장
        List<ProductOptionGroupEntity> productOptionGroupEntityList = request.optionGroups().stream()
                .map(it -> ProductOptionGroupEntity.create(it, savedProductEntity))
                .toList();
        productOptionGroupRepository.saveAll(productOptionGroupEntityList);

        // image 저장
        List<ProductImageEntity> productImageEntityList = request.images().stream()
                .map(it -> ProductImageEntity.create(it, savedProductEntity))
                .toList();
        productImageRepository.saveAll(productImageEntityList);

        // tag 존재 여부 확인
        List<TagEntity> tagEntityList = tagRepository.findAllById(request.tags());
        Map<Long, TagEntity> tagEntityMap = tagEntityList.stream().collect(Collectors.toMap(
                TagEntity::getId,
                Function.identity(),
                (existing, replacement) -> existing
        ));
        if (tagEntityList.size() != request.tags().size()) {
            throw new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_CREATE_FAIL);
        }
        // product tag 저장
        List<ProductTagEntity> productTagEntityList = request.tags().stream()
                .map(it -> ProductTagEntity.create(savedProductEntity, tagEntityMap.get(it)))
                .toList();
        productTagRepository.saveAll(productTagEntityList);

        return new CreateProductResult(
                savedProductEntity.getId(),
                savedProductEntity.getName(),
                savedProductEntity.getSlug(),
                savedProductEntity.getCreatedAt(),
                savedProductEntity.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public ProductDetail findProductDetail(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_FIND_FAIL));
        List<ProductCategoryEntity> productCategoryEntityList = productCategoryRepository.findAllByProductId(productId);
        List<ProductOptionEntity> productOptionEntityList = productOptionRepository.findAllByProductId(productId);
        List<ProductTagEntity> productTagEntityList = productTagRepository.findAllByProductId(productId);
        ReviewDistribution reviewDistribution = reviewService.findDistributionByProductId(productId);

        Map<Long, List<ProductOptionEntity>> productOptionEntityMap = productOptionEntityList.stream()
                .collect(Collectors.groupingBy(
                        productOptionEntity -> productOptionEntity.getProductOptionGroupEntity().getId()
                ));

        List<ProductOptionGroupEntity> productOptionGroupEntityList = productOptionEntityList.stream()
                .map(ProductOptionEntity::getProductOptionGroupEntity)
                .distinct()
                .toList();

        List<Long> categoryIdList = productCategoryEntityList.stream().map(ProductCategoryEntity::getCategoryEntity)
                .map(CategoryEntity::getId)
                .toList();
        List<ProductCategoryEntity> relatedProductCategoryEntityList = productCategoryRepository.findAllByCategoryIds(categoryIdList);
        Map<Long, ProductEntity> relatedProductEntityMap = relatedProductCategoryEntityList.stream()
                .map(ProductCategoryEntity::getProductEntity)
                .collect(Collectors.toMap(
                        ProductEntity::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
        Set<Long> productIds = new HashSet<>(relatedProductEntityMap.keySet());
        productIds.add(productId);

        List<ProductImageEntity> productImageEntityList = productImageRepository.findAllByProductIds(productIds);
        Map<Long, List<ProductImageEntity>> productImageMap = productImageEntityList.stream()
                .collect(Collectors.groupingBy(
                        image -> image.getProductEntity().getId()
                ));

        return ProductDetail.of(
                productEntity,
                productCategoryEntityList,
                productImageMap,
                productOptionGroupEntityList,
                productOptionEntityMap,
                productTagEntityList,
                reviewDistribution,
                relatedProductEntityMap
        );
    }

    public UpdateProductResult updateProduct(Long productId,
                                              UpdateProductRequest request
    ) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_UPDATE_FAIL));
        Boolean alreadyExistSlug = productRepository.existBySlug(request.slug());
        if (Boolean.TRUE.equals(alreadyExistSlug) && !Objects.equals(productEntity.getId(), productId)) {
            throw new GlobalException(ErrorCode.CONFLICT, ProductError.PRODUCT_UPDATE_FAIL);
        }
        SellerEntity sellerEntity = sellerRepository.findById(request.sellerId())
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_UPDATE_FAIL));
        BrandEntity brandEntity = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_UPDATE_FAIL));
        List<Long> categoryIds = request.categories().stream()
                .map(ProductCategoryRequest::categoryId)
                .toList();

        productEntity.update(request);
        productEntity.updateSeller(sellerEntity);
        productEntity.updateBrand(brandEntity);
        productRepository.save(productEntity);

        // category 존재 여부 확인
        productCategoryRepository.deleteByProductId(productId);
        CategoryQueryFilter filter = CategoryQueryFilter.builder()
                .categoryIds(categoryIds)
                .build();
        List<CategoryEntity> categoryEntityList = categoryRepository.findAllByFilter(filter);
        Map<Long, CategoryEntity> categoryEntityMap = categoryEntityList.stream()
                .collect(Collectors.toMap(
                        CategoryEntity::getId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));
        if (categoryEntityList.size() != request.categories().size()) {
            throw new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_CREATE_FAIL);
        }
        // product category 업데이트
        List<ProductCategoryEntity> productCategoryEntityList = request.categories().stream().map(it ->
                ProductCategoryEntity.create(
                        productEntity,
                        categoryEntityMap.get(it.categoryId()),
                        it.isPrimary()
                )
        ).toList();
        productCategoryRepository.saveAll(productCategoryEntityList);

        // productOptionGroup 업데이트
        productOptionGroupRepository.deleteByProductId(productId);
        List<ProductOptionGroupEntity> productOptionGroupEntityList = request.optionGroups().stream()
                .map(it -> ProductOptionGroupEntity.create(it, productEntity))
                .toList();
        productOptionGroupRepository.saveAll(productOptionGroupEntityList);

        // product image 업데이트
        productImageRepository.deleteByProductId(productId);
        List<ProductImageEntity> productImageEntityList = request.images().stream()
                .map(it -> ProductImageEntity.create(it, productEntity))
                .toList();
        productImageRepository.saveAll(productImageEntityList);

        // tag 확인
        List<TagEntity> tagEntityList = tagRepository.findAllById(request.tags());
        Map<Long, TagEntity> tagEntityMap = tagEntityList.stream().collect(Collectors.toMap(
                TagEntity::getId,
                Function.identity(),
                (existing, replacement) -> existing
        ));
        if (tagEntityList.size() != request.tags().size()) {
            throw new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_CREATE_FAIL);
        }
        // product tag 저장
        productTagRepository.deleteByProductId(productId);
        List<ProductTagEntity> productTagEntityList = request.tags().stream()
                .map(it -> ProductTagEntity.create(productEntity, tagEntityMap.get(it)))
                .toList();
        productTagRepository.saveAll(productTagEntityList);

        return new UpdateProductResult(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getSlug(),
                productEntity.getCreatedAt(),
                productEntity.getUpdatedAt()
        );
    }

    public void deleteById(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_DELETE_FAIL));
        if (ProductStatus.DELETED == productEntity.getStatus()) {
            throw new GlobalException(ErrorCode.CONFLICT, ProductError.PRODUCT_DELETE_FAIL);
        }
        productEntity.softDelete();
        productRepository.save(productEntity);
    }

    public AddProductImageResult addProductImage(Long productId, AddProductImageRequest request) {
        ProductOptionEntity productOptionEntity = productOptionRepository.findByIdAndProductId(request.optionId(), productId)
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, ProductError.PRODUCT_IMAGE_ADD_FAIL));
        ProductEntity productEntity = productOptionEntity.getProductOptionGroupEntity().getProductEntity();

        ProductImageEntity productImageEntity = ProductImageEntity.create(request, productEntity);

        ProductImageEntity save = productImageRepository.save(productImageEntity);

        return new AddProductImageResult(
                save.getId(),
                save.getUrl(),
                save.getAltText(),
                save.getIsPrimary(),
                save.getDisplayOrder(),
                save.getOptionId()
        );
    }
}
