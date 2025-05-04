package com.ecommerce.products.application.service;

import com.ecommerce.products.application.dto.PaginationDto;
import com.ecommerce.products.application.dto.ProductDto;
import com.ecommerce.products.application.mapper.ProductMapper;
import com.ecommerce.products.controller.dto.ProductListResponse;
import com.ecommerce.products.exception.ResourceNotFoundException;
import com.ecommerce.products.infra.jpa.entity.*;
import com.ecommerce.products.infra.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final SellerRepository sellerRepository;
    private final TagRepository tagRepository;
    private final ProductOptionGroupRepository optionGroupRepository;
    private final ProductOptionRepository optionRepository;
    private final ProductImageRepository imageRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductDto.Product createProduct(ProductDto.CreateRequest request) {
        // 1. 기본 Product 엔티티 생성
        Product product = productMapper.toProductEntity(request);

        // 2. 연관 엔티티 설정
        if (request.getSellerId() != null) {
            Seller seller = sellerRepository.findById(request.getSellerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seller", request.getSellerId()));
            product.setSeller(seller);
        }

        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand", request.getBrandId()));
            product.setBrand(brand);
        }

        // 3. 저장 및 ID 획득
        product = productRepository.save(product);

        // 4. 연관 관계 설정 및 저장
        // ProductDetail 생성 및 저장
        if (request.getDetail() != null) {
            ProductDetail detail = productMapper.toProductDetailEntity(request.getDetail(), product);
            product.setDetail(detail);
        }

        // ProductPrice 생성 및 저장
        if (request.getPrice() != null) {
            ProductPrice price = productMapper.toProductPriceEntity(request.getPrice(), product);
            product.setPrice(price);
        }

        // 카테고리 연결
        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            List<Long> categoryIds = request.getCategories().stream()
                    .map(ProductDto.ProductCategory::getCategoryId)
                    .toList();
            List<Category> categories = categoryRepository.findAllById(categoryIds);
            product.getCategories().addAll(categories);
        }

        // 태그 연결
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());
            product.getTags().addAll(tags);
        }

        // 옵션 그룹 및 옵션 생성
        if (request.getOptionGroups() != null) {
            for (ProductDto.OptionGroup groupDto : request.getOptionGroups()) {
                ProductOptionGroup group = productMapper.toProductOptionGroupEntity(groupDto, product);
                product.getOptionGroups().add(group);

                // 옵션 생성
                if (groupDto.getOptions() != null) {
                    for (ProductDto.Option optionDto : groupDto.getOptions()) {
                        ProductOption option = productMapper.toProductOptionEntity(optionDto, group);
                        group.getOptions().add(option);
                    }
                }
            }
        }

        // 이미지 생성
        if (request.getImages() != null) {
            for (ProductDto.Image imageDto : request.getImages()) {
                ProductOption option = null;
                if (imageDto.getOptionId() != null) {
                    option = optionRepository.findById(imageDto.getOptionId())
                            .orElse(null);
                }
                ProductImage image = productMapper.toProductImageEntity(imageDto, product, option);
                product.getImages().add(image);
            }
        }

        // 최종 저장 및 응답 생성
        product = productRepository.save(product);
        return productMapper.toProductDto(product);
    }



    @Override
    @Transactional
    public ProductDto.Product updateProduct(Long productId, ProductDto.UpdateRequest request) {
        Product product = productRepository.findById(productId)
                .map(entity -> productMapper.updateProductEntity(request, entity))
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        // 연관 엔티티 업데이트
        if (request.getSellerId() != null) {
            Seller seller = sellerRepository.findById(request.getSellerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seller", request.getSellerId()));
            product.setSeller(seller);
        }

        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand", request.getBrandId()));
            product.setBrand(brand);
        }

        // ProductDetail 업데이트
        if (request.getDetail() != null && product.getDetail() != null) {
            productMapper.updateProductDetailEntity(request.getDetail(), product.getDetail());
        }

        // ProductPrice 업데이트
        if (request.getPrice() != null && product.getPrice() != null) {
            productMapper.updateProductPriceEntity(request.getPrice(), product.getPrice());
        }

        // 카테고리 업데이트
        if (request.getCategories() != null) {
            product.getCategories().clear();
            List<Long> categoryIds = request.getCategories().stream()
                    .map(ProductDto.ProductCategory::getCategoryId)
                    .toList();
            List<Category> categories = categoryRepository.findAllById(categoryIds);
            product.getCategories().addAll(categories);
        }

        // 태그 업데이트
        if (request.getTagIds() != null) {
            product.getTags().clear();
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());
            product.getTags().addAll(tags);
        }

        // 저장 및 응답 생성
        product = productRepository.save(product);

        return productMapper.toProductDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        // 소프트 삭제
        product.setStatus(ProductStatus.DELETED);
        productRepository.save(product);

        // 하드 삭제가 필요한 경우 사용
        // productRepository.delete(product);
    }

    @Override
    @Transactional
    public ProductDto.Option addProductOption(Long productId, ProductDto.Option request) {
        var optionGroupId = request.getOptionGroupId();

        ProductOptionGroup optionGroup = optionGroupRepository.findById(optionGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("OptionGroup", optionGroupId));

        // 해당 옵션 그룹이 요청된 상품에 속하는지 확인
        if (!optionGroup.getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("OptionGroup does not belong to the specified product");
        }

        // OptionDto 생성 및 변환
        ProductDto.Option optionDto = ProductDto.Option.builder()
                .optionGroupId(optionGroupId)
                .name(request.getName())
                .additionalPrice(request.getAdditionalPrice())
                .sku(request.getSku())
                .stock(request.getStock())
                .displayOrder(request.getDisplayOrder())
                .build();

        // 옵션 엔티티 생성 및 저장
        ProductOption option = productMapper.toProductOptionEntity(optionDto, optionGroup);
        option = optionRepository.save(option);

        return productMapper.toOptionDto(option);
    }

    @Override
    @Transactional
    public ProductDto.Option updateProductOption(Long productId, ProductDto.Option request) {
        var optionId = request.getId();

        ProductOption option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Option", optionId));

        // 해당 옵션이 요청된 상품에 속하는지 확인
        if (!option.getOptionGroup().getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("Option does not belong to the specified product");
        }

        // 옵션 업데이트
        if (request.getName() != null) {
            option.setName(request.getName());
        }

        if (request.getAdditionalPrice() != null) {
            option.setAdditionalPrice(request.getAdditionalPrice());
        }

        if (request.getSku() != null) {
            option.setSku(request.getSku());
        }

        if (request.getStock() != null) {
            option.setStock(request.getStock());
        }

        if (request.getDisplayOrder() != null) {
            option.setDisplayOrder(request.getDisplayOrder());
        }

        option = optionRepository.save(option);
        return productMapper.toOptionDto(option);
    }

    @Override
    @Transactional
    public void deleteProductOption(Long productId, Long optionId) {
        ProductOption option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Option", optionId));

        // 해당 옵션이 요청된 상품에 속하는지 확인
        if (!option.getOptionGroup().getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("Option does not belong to the specified product");
        }

        optionRepository.delete(option);
    }

    @Override
    @Transactional
    public ProductDto.Image addProductImage(Long productId, ProductDto.Image request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        ProductOption option = null;
        if (request.getOptionId() != null) {
            option = optionRepository.findById(request.getOptionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Option", request.getOptionId()));

            // 해당 옵션이 요청된 상품에 속하는지 확인
            if (!option.getOptionGroup().getProduct().getId().equals(productId)) {
                throw new IllegalArgumentException("Option does not belong to the specified product");
            }
        }

        // 이미지 엔티티 생성 및 저장
        ProductImage image = productMapper.toProductImageEntity(request, product, option);
        image = imageRepository.save(image);

        return productMapper.toImageDto(image);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto.Product getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        return productMapper.toProductDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductListResponse getProducts(ProductDto.ListRequest request) {
        // Specification 생성 및 조합
        Specification<Product> spec = Specification.where(null);

        // 상태 필터
        if (request.getStatus() != null) {
            spec = spec.and(ProductSpecification.withStatus(request.getStatus()));
        }

        // 가격 범위 필터
        if (request.getMinPrice() != null) {
            spec = spec.and(ProductSpecification.withMinPrice(request.getMinPrice()));
        }

        if (request.getMaxPrice() != null) {
            spec = spec.and(ProductSpecification.withMaxPrice(request.getMaxPrice()));
        }

        // 카테고리 필터
        if (request.getCategory() != null && !request.getCategory().isEmpty()) {
            spec = spec.and(ProductSpecification.withCategoryId(request.getCategory()));
        }

        // 판매자 필터
        if (request.getSeller() != null) {
            spec = spec.and(ProductSpecification.withSellerId(request.getSeller()));
        }

        // 브랜드 필터
        if (request.getBrand() != null) {
            spec = spec.and(ProductSpecification.withBrandId(request.getBrand()));
        }

        // 태그 필터
        if (request.getTag() != null && !request.getTag().isEmpty()) {
            spec = spec.and(ProductSpecification.withTagIds(request.getTag()));
        }

        // 재고 여부 필터
        if (request.getInStock() != null) {
            spec = spec.and(ProductSpecification.inStock(request.getInStock()));
        }

        // 검색어 필터
        if (request.getSearch() != null && !request.getSearch().isEmpty()) {
            spec = spec.and(ProductSpecification.withSearch(request.getSearch()));
        }

        // 등록일 범위 필터
        if (request.getCreatedFrom() != null) {
            LocalDateTime fromDate = request.getCreatedFrom().atStartOfDay();
            spec = spec.and(ProductSpecification.withCreatedDateAfter(fromDate));
        }

        if (request.getCreatedTo() != null) {
            // 날짜의 끝(23:59:59)으로 설정
            LocalDateTime toDate = request.getCreatedTo().plusDays(1).atStartOfDay().minusSeconds(1);
            spec = spec.and(ProductSpecification.withCreatedDateBefore(toDate));
        }

        // 조회 실행
        Page<Product> productPage = productRepository.findAll(spec, request.getPagination().toPageable());

        // 결과 변환
        List<ProductDto.ProductSummary> productSummaries = productPage.stream()
                .map(productMapper::toProductSummaryDto)
                .toList();

        // 페이지네이션 정보 생성
        PaginationDto.PaginationInfo paginationInfo = PaginationDto.PaginationInfo.builder()
                .totalItems((int) productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .currentPage(request.getPagination().getPage())
                .perPage(request.getPagination().getSize())
                .build();

        // 응답 생성
        return ProductListResponse.builder()
                .items(productSummaries)
                .pagination(paginationInfo)
                .build();
    }
}