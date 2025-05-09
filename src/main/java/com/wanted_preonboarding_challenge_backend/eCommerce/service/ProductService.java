package com.wanted_preonboarding_challenge_backend.eCommerce.service;


import com.wanted_preonboarding_challenge_backend.eCommerce.common.PaginationDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.domain.*;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.Image.request.ImageAddRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.Image.response.ImageAddResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.ProductSummaryDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.request.ProductOptionAddRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.request.ProductOptionUpdateRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.response.ProductOptionAddResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.response.ProductOptionUpdateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.*;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response.ProductCreateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response.ProductDetailResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response.ProductListResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response.ProductUpdateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.mapper.*;
import com.wanted_preonboarding_challenge_backend.eCommerce.respository.*;
import com.wanted_preonboarding_challenge_backend.eCommerce.util.JsonConverter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductPriceRepository productPriceRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;

    private final ProductMapper productMapper;
    private final ProductOptionGroupMapper productOptionGroupMapper;
    private final ProductOptionMapper productOptionMapper;
    private final ProductPriceMapper productPriceMapper;
    private final ProductDetailMapper productDetailMapper;
    private final ProductImageMapper productImageMapper;
    private final SellerMapper sellerMapper;
    private final BrandMapper brandMapper;
    private final PriceMapper priceMapper;
    private final DetailMapper detailMapper;


    public ProductListResponse getProductList(ProductsSearchCondition condition) {
        Pageable pageable = PageRequest.of(
                condition.getPage() - 1,
                condition.getPerPage(),
                parseSort(condition.getSort())
        );

        Page<ProductSummaryDto> page = productRepository.findByCondition(condition, pageable);

        List<ProductSummaryDto> items = page.getContent();

        return ProductListResponse.builder()
                .items(items)
                .pagination(PaginationDto.builder()
                        .totalItems(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .currentPage(condition.getPage())
                        .perPage(condition.getPerPage())
                        .build())
                .build();
    }



    public ProductDetailResponse getProduct(Long id){
        Product product = productRepository.findWithSellerBrandPriceDetailById(id);

        List<ProductCategory> categories = productCategoryRepository.findByProductId(id);
        List<ProductOptionGroup> optionGroups = productOptionGroupRepository.findByProductId(id);
        List<ProductImage> images = productImageRepository.findByProductId(id);
        List<ProductTag> tags = productTagRepository.findByProductId(id);

        ProductDetailResponse response = ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .shortDescription(product.getShortDescription())
                .seller(sellerMapper.toSellerDetailDto(product.getSeller()))
                .brand(brandMapper.toBrandDetailDto(product.getBrand()))
                .price(priceMapper.toPriceDto(product.getPrice()))
                .detail(detailMapper.toDetailDto(product.getDetail()))
//                .categories(categoryMapper.toDtoList(categories))
//                .optionGroups(optionGroupMapper.toDtoList(optionGroups))
//                .images(imageMapper.toDtoList(images))
//                .tags(tagMapper.toDtoList(tags))
//                .rating()
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();

        return response;

    }

    public ProductListResponse getProductListByCategoryId(Long categoryId, ProductsByCategoryCondition condition) {
        Pageable pageable = PageRequest.of(
                condition.getPage() - 1,
                condition.getPerPage(),
                parseSort(condition.getSort())
        );

        Page<ProductSummaryDto> page = productRepository.findByCondition(condition, pageable);

        List<ProductSummaryDto> items = page.getContent();

        return ProductListResponse.builder()
                .items(items)
                .pagination(PaginationDto.builder()
                        .totalItems(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .currentPage(condition.getPage())
                        .perPage(condition.getPerPage())
                        .build())
                .build();
    }

    private Sort parseSort(String sort) {
        if (sort == null || !sort.contains(":")) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        String[] parts = sort.split(":");
        String property = parts[0];
        Sort.Direction direction = parts[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        return Sort.by(direction, property);
    }



    public ProductCreateResponse writeProduct(ProductCreateRequest request) {
        //valid 추가

        Product product = addProduct(request);

        // product detail
        addProductDetail(request.getDetail(), product);
        // product price
        addProductPrice(request.getPrice(), product);

        // productCategory
        addProductCategory(request, product);

        // optiongroup + option
        addOptionGroup(request, product);

        // image
        addProductImage(request.getImages(), product);

        //product tag
        addProductTag(request, product);

        return productMapper.toProductCreateResponse(product);
    }

    private Product addProduct(ProductCreateRequest request) {
        // seller
        Seller seller = sellerRepository.findById(request.getSellerId()).orElseThrow(() -> new RuntimeException());
        // brand
        Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(() -> new RuntimeException());

        Product product = productMapper.toEntity(request);

        product.addSeller(seller);
        product.addBrand(brand);

        return productRepository.save(product);
    }

    private void addOptionGroup(ProductCreateRequest request, Product product) {

        request.getOptionGroups().forEach(productOptionGroupCreateRequest -> {

            ProductOptionGroup productOptionGroup =
                    productOptionGroupMapper.toEntity(productOptionGroupCreateRequest);
            productOptionGroup.addProduct(product);

            productOptionGroupRepository.save(productOptionGroup);

            productOptionGroupCreateRequest.getOptions().forEach(optionCreateRequest -> {
                ProductOption productOption =
                        productOptionMapper.toEntity(optionCreateRequest);
                productOption.addOptionGroup(productOptionGroup);

                productOptionRepository.save(productOption);
            });
        });
    }

    private void addProductDetail(ProductDetailCreateRequest request, Product product) {
        ProductDetail productDetail = productDetailMapper.toEntity(request);
        productDetail.addProduct(product);

        productDetailRepository.save(productDetail);
    }

    private void addProductPrice(ProductPriceCreateRequest request, Product product) {

        ProductPrice productPrice = productPriceMapper.toEntity(request);
        productPrice.addProduct(product);

        productPriceRepository.save(productPrice);
    }

    private void addProductImage(List<ProductImageCreateRequest> images, Product product) {
        images.forEach(image -> {
            ProductImage productImage = productImageMapper.toEntity(image);
            if (image.getOptionId() != null) {
                ProductOption option = productOptionRepository.findById(image.getOptionId())
                        .orElseThrow(() -> new IllegalArgumentException("해당 옵션이 존재하지 않습니다."));

                productImage.addProductOption(option);
            }
            productImage.addProduct(product);
            productImageRepository.save(productImage);

        });
    }

    private void addProductTag(ProductCreateRequest request, Product product) {
        List<Tag> tags = tagRepository.findAllById(request.getTags());
        Map<Long, Tag> tagMap = tags.stream()
                .collect(Collectors.toMap(Tag::getId, Function.identity()));

        request.getTags().forEach(tag -> {

            ProductTag productTag = ProductTag.builder()
                    .product(product)
                    .tag(tagMap.get(tag))
                    .build();
            productTagRepository.save(productTag);
        });
    }

    private void addProductCategory(ProductCreateRequest request, Product product) {
        List<Long> categoryIds = request.getCategories().stream().map(ProductCategoryCreateRequest::getCategoryId).collect(Collectors.toList());
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        Map<Long, Category> categoryMap = categories.stream()
                .collect(Collectors.toMap(Category::getId, Function.identity()));

        request.getCategories().forEach(productCategoryCreateRequest -> {
            Long categoryId = productCategoryCreateRequest.getCategoryId();

            ProductCategory productCategory =
                    ProductCategory.builder()
                            .product(product)
                            .category(categoryMap.get(categoryId))
                            .isPrimary(productCategoryCreateRequest.getIsPrimary())
                            .build();

            productCategoryRepository.save(productCategory);
        });
    }

    public ProductUpdateResponse updateProduct(Long id, ProductCreateRequest request) {
        Product product = productRepository.findWithSellerBrandPriceDetailById(id);

        Seller seller = sellerRepository.findById(request.getSellerId()).orElseThrow(() -> new RuntimeException());
        Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(() -> new RuntimeException());

        product.update(request.getName(), request.getSlug(), request.getShortDescription(), request.getFullDescription(), request.getStatus());
        product.updateSeller(seller);
        product.updateBrand(brand);

        // toOne (detail, price) 정보 변경하기
        product.getPrice().update(
                request.getPrice().getBasePrice().doubleValue(),
                request.getPrice().getSalePrice().doubleValue(),
                request.getPrice().getCostPrice().doubleValue(),
                request.getPrice().getCurrency(),
                request.getPrice().getTaxRate().doubleValue()
        );

        product.getDetail().update(
                request.getDetail().getWeight(),
                JsonConverter.toJson(request.getDetail().getDimensions()),
                request.getDetail().getMaterials(),
                request.getDetail().getCountryOfOrigin(),
                request.getDetail().getWarrantyInfo(),
                request.getDetail().getCareInstructions(),
                JsonConverter.toJson(request.getDetail().getAdditionalInfo())
        );

        // toMany
        // 1. productCategory
        productCategoryRepository.deleteByProductId(product.getId());
        addProductCategory(request, product);

        // 2.productTag
        productTagRepository.deleteByProductId(product.getId());
        addProductTag(request, product);

        // 3.productOptionGroup + option + image
        productOptionGroupRepository.deleteByProductId(product.getId());

        // 4.productImage
        addProductImage(request.getImages(), product);

        return productMapper.toProductUpdateResponse(product);

    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("옵션 그룹이 존재하지 않습니다."));
        product.deleteProduct();
    }

    public ProductOptionAddResponse addProductOption(Long id, ProductOptionAddRequest request){
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("옵션 그룹이 존재하지 않습니다."));
        ProductOptionGroup productOptionGroup = productOptionGroupRepository.findById(request.getOptionGroupId()).orElseThrow(() -> new IllegalArgumentException("옵션 그룹이 존재하지 않습니다."));

        ProductOption productOption = productOptionMapper.toEntity(request);

        productOptionGroup.addOption(productOption);
        productOptionRepository.save(productOption);

        return productOptionMapper.toProductOptionAddResponse(productOption);

    }


    public ProductOptionUpdateResponse updateProductOption(Long id, Long optionId, ProductOptionUpdateRequest optionUpdateRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("옵션 그룹이 존재하지 않습니다."));
        ProductOption productOption = productOptionRepository.findById(optionId).orElseThrow(() -> new IllegalArgumentException("옵션 그룹이 존재하지 않습니다."));

        productOption.updateProductOption(
                optionUpdateRequest.getName(),
                optionUpdateRequest.getAdditionalPrice().doubleValue(),
                optionUpdateRequest.getSku(),
                optionUpdateRequest.getStock(),
                optionUpdateRequest.getDisplayOrder()
        );

        return productOptionMapper.toProductOptionUpdateResponse(productOption);
    }

    public ImageAddResponse addImage(Long id, ImageAddRequest request){
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("옵션 그룹이 존재하지 않습니다."));
        ProductOption productOption = productOptionRepository.findById(request.getOptionId()).orElseThrow(() -> new IllegalArgumentException("옵션 그룹이 존재하지 않습니다."));

        ProductImage productImage = productImageMapper.toEntity(request);

        productImage.addProductOption(productOption);
        productImageRepository.save(productImage);

        return productImageMapper.toImageAddResponse(productImage);
    }
}
