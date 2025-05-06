package com.wanted.mono.domain.product.service;

import com.wanted.mono.domain.brand.entity.Brand;
import com.wanted.mono.domain.brand.service.BrandService;
import com.wanted.mono.domain.product.dto.Pagination;
import com.wanted.mono.domain.product.dto.ProductSearchItem;
import com.wanted.mono.domain.product.dto.model.ProductImageDto;
import com.wanted.mono.domain.product.dto.model.ProductInfoDto;
import com.wanted.mono.domain.product.dto.request.*;
import com.wanted.mono.domain.product.dto.response.ProductOptionSaveResponse;
import com.wanted.mono.domain.product.dto.response.ProductSaveResponse;
import com.wanted.mono.domain.product.dto.response.ProductSearchResponse;
import com.wanted.mono.domain.product.dto.response.ProductUpdateResponse;
import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.product.entity.ProductOptionGroup;
import com.wanted.mono.domain.product.repository.query.ProductInfoQueryRepository;
import com.wanted.mono.domain.product.repository.query.ProductSearchQueryRepository;
import com.wanted.mono.domain.product.repository.ProductRepository;
import com.wanted.mono.domain.seller.entity.Seller;
import com.wanted.mono.domain.seller.service.SellerService;
import com.wanted.mono.domain.tag.service.ProductTagService;
import com.wanted.mono.global.exception.ProductEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailService productDetailService;
    private final ProductPriceService productPriceService;
    private final ProductCategoryService productCategoryService;
    private final ProductOptionGroupService productOptionGroupService;
    private final ProductImageService productImageService;
    private final ProductTagService productTagService;
    private final SellerService sellerService;
    private final BrandService brandService;
    private final ProductSearchQueryRepository productSearchQueryRepository;
    private final ProductItemService productItemService;
    private final ProductInfoQueryRepository productInfoQueryRepository;
    private final ProductOptionService productOptionService;

    @Transactional
    public ProductSaveResponse createProduct(ProductRequest productRequest) {
        Product product = Product.of(productRequest);
        productRepository.save(product);

        log.info("Product 서브 엔티티 저장 시작");
        Long savedProductDetailId = productDetailService.createProductDetail(productRequest.getDetail(), product);
        Long savedProductPriceId = productPriceService.createProductPrice(productRequest.getPrice(), product);
        productCategoryService.createProductCategory(productRequest.getCategories(), product);
        productOptionGroupService.createProductOptionGroup(productRequest.getOptionGroups(), product);
        productImageService.createProductImages(productRequest.getImages(), product);
        productTagService.createProductTag(productRequest.getTags(), product);
        log.info("Product 서브 엔티티 저장 종료");

        log.info("seller, brand 연관관계 추가");
        Seller seller = sellerService.findById(productRequest.getSellerId());
        Brand brand = brandService.findById(productRequest.getBrandId());
        product.addSeller(seller);
        product.addBrand(brand);

        log.info("Product 엔티티 영속성 저장");

        Long productId = product.getId();
        String name = product.getName();
        String slug = product.getSlug();
        LocalDateTime createdAt = product.getCreatedAt();
        LocalDateTime updatedAt = product.getUpdatedAt();
        return new ProductSaveResponse(productId, name, slug, createdAt, updatedAt);
    }

    public ProductSearchResponse searchProduct(ProductSearchRequest request) {
        log.info("ProductService 페이징 및 필터 처리 조회");
        Page<Product> searchResult = productSearchQueryRepository.search(request);

        List<ProductSearchItem> displayProducts = new ArrayList<>();
        for (Product product : searchResult.getContent()) {
            ProductSearchItem dto = productItemService.toDto(product);
            displayProducts.add(dto);
        }

        Pagination pagination = new Pagination(
                (int) searchResult.getTotalElements(),
                searchResult.getTotalPages(),
                searchResult.getNumber() + 1,
                searchResult.getSize()
        );


        return new ProductSearchResponse(displayProducts, pagination);
    }

    @Transactional
    public ProductUpdateResponse updateProduct(Long productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId).orElseThrow(ProductEmptyException::new);

        log.info("ProductService 엔티티 기본 정보 업데이트");
        product.updateFrom(productRequest);

        log.info("ProductService ProductDetail 정보 업데이트");
        productDetailService.updateProductDetail(productRequest.getDetail(), product);

        log.info("ProductService ProductPrice 정보 업데이트");
        productPriceService.updateProductPrice(productRequest.getPrice(), product);

        log.info("ProductService ProductCategory 정보 업데이트");
        productCategoryService.updateProductCategory(productRequest.getCategories(), product);

        return new ProductUpdateResponse(productId, productRequest.getName(), productRequest.getSlug(), product.getUpdatedAt());
    }

    @Transactional
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductEmptyException();
        }

        productRepository.deleteById(productId);
    }
    public ProductInfoDto findById(Long productId) {
        return productInfoQueryRepository.getProductInfo(productId);
    }

    @Transactional
    public ProductOptionSaveResponse addOption(ProductAddOptionRequest addOptionRequest, Long productId) {
        ProductOptionGroup productOptionGroup = productOptionGroupService.findById(addOptionRequest.getOptionGroupId(), productId);

        ProductOptionRequest productOptionRequest = new ProductOptionRequest(
                addOptionRequest.getName(),
                addOptionRequest.getAdditionalPrice(),
                addOptionRequest.getSku(),
                addOptionRequest.getStock(),
                addOptionRequest.getDisplayOrder());

        Long saveId = productOptionService.createProductOption(productOptionRequest, productOptionGroup);
        return new ProductOptionSaveResponse(
                saveId,
                addOptionRequest.getOptionGroupId(),
                addOptionRequest.getName(),
                addOptionRequest.getAdditionalPrice(),
                addOptionRequest.getSku(),
                addOptionRequest.getStock(),
                addOptionRequest.getDisplayOrder());
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        productOptionService.deleteOption(optionId);
    }

    @Transactional
    public ProductImageDto addImage(Long productId, ProductImageRequest productImageRequest) {
        Product product = productRepository.findById(productId).orElseThrow(ProductEmptyException::new);
        Long saveId = productImageService.createProductImage(productImageRequest, product);

        return new ProductImageDto(
                saveId,
                productImageRequest.getUrl(),
                productImageRequest.getAltText(),
                productImageRequest.getIsPrimary(),
                productImageRequest.getDisplayOrder(),
                productImageRequest.getOptionId());
    }
}
