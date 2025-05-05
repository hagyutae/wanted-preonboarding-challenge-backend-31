package com.example.cqrsapp.product.service;

import com.example.cqrsapp.aop.HandleDuplicateKey;
import com.example.cqrsapp.common.dto.ProductSummaryItem;
import com.example.cqrsapp.common.dto.ReviewRatingDto;
import com.example.cqrsapp.common.dto.ReviewRatingDto.DistributionDto;
import com.example.cqrsapp.common.exception.ResourceNotFoundException;
import com.example.cqrsapp.common.response.PageResponseDto;
import com.example.cqrsapp.product.domain.*;
import com.example.cqrsapp.product.dto.requset.RegisterProductDto;
import com.example.cqrsapp.product.dto.requset.RegisterProductOptionDto;
import com.example.cqrsapp.product.dto.requset.UpdateProductDto;
import com.example.cqrsapp.product.dto.response.ProductResponse;
import com.example.cqrsapp.product.dto.response.RegisterProductOptionResponseDto;
import com.example.cqrsapp.product.dto.response.RegisterProductResponseDto;
import com.example.cqrsapp.product.dto.response.UpdateProductResponse;
import com.example.cqrsapp.product.repository.*;
import com.example.cqrsapp.review.repository.ReviewRepository;
import com.example.cqrsapp.seller.domain.Seller;
import com.example.cqrsapp.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.cqrsapp.product.dto.requset.RegisterProductDto.CategoryDto;
import static com.example.cqrsapp.product.dto.response.ProductResponse.*;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final ProductOptionGroupJapRepository productOptionGroupJapRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductMapper mapper;

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productId) {
        Product product = findEntityGraphById(productId);
        ReviewRatingDto rating = reviewRepository.findReviewRatingByProductId(product.getId());
        Map<String, Long> distributionMap = getDistributionMap(product);
        List<Product> relatedProducts = getRelatedProducts(product);
        return fromEntity(product, rating, distributionMap, relatedProducts);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<ProductSummaryItem> searchProduct(SearchParm searchParm, Pageable pageable) {
        Page<ProductSummaryItem> products = productRepository.findAll(searchParm, pageable);
        return PageResponseDto.from(products);
    }

    @HandleDuplicateKey
    @Transactional
    public RegisterProductResponseDto createProduct(RegisterProductDto dto) {
        Seller seller = findSeller(dto.getSellerId());
        Brand brand = findBrand(dto.getSellerId(), dto.getBrandId());
        Product product = mapper.mapProductFromDto(dto, seller, brand);

        productRepository.save(product);
        saveProductSeries(product, dto.getCategories(), dto.getTags());
        return RegisterProductResponseDto.fromEntity(product);
    }

    @HandleDuplicateKey
    @Transactional
    public UpdateProductResponse updateProduct(Long productId, UpdateProductDto dto) {
        Product product = findProduct(productId);
        Seller seller = findSeller(dto.getSellerId());
        Brand brand = findBrand(dto.getSellerId(), dto.getBrandId());

        product.changeProduct(
                productId,
                dto.getName(),
                dto.getSlug(),
                dto.getShortDescription(),
                dto.getFullDescription(),
                seller,
                brand,
                dto.getStatus(),
                mapper.toProductDetail(dto.getDetail()),
                mapper.toProductPrice(dto.getPrice()));

        updateProductSeries(product, dto);
        return UpdateProductResponse.fromEntity(product);
    }

    @Transactional
    public RegisterProductOptionResponseDto addProductOption(Long productId, RegisterProductOptionDto dto) {
        ProductOptionGroup productOptionGroup = findOptionGroup(dto.getOptionGroupId());
        ProductOption productOption = mapper.mapProductOptionFromDto(productOptionGroup, dto);

        productOptionRepository.save(productOption);
        return RegisterProductOptionResponseDto.fromEntity(productOption);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = findProduct(productId);
        productRepository.deleteById(productId);
    }

    private Map<String, Long> getDistributionMap(Product product) {
        List<DistributionDto> distribution = reviewRepository.findReviewDistributionByProductId(product.getId());
        return distribution.stream()
                .collect(Collectors.toMap(DistributionDto::getRating,
                        DistributionDto::getCount));
    }

    private List<Product> getRelatedProducts(Product product) {
        List<Tag> tag = product.getProductTags().stream().map(ProductTag::getTag).toList();
        return productRepository.findByTagIn(tag);
    }

    private void saveProductSeries(Product product, List<CategoryDto> categoryDto, List<Long> tagIds) {
        saveProductCategories(product, categoryDto);
        saveProductTags(product, tagIds);
    }

    private void saveProductTags(Product product, List<Long> tagIds) {
        List<Tag> tags = tagRepository.findByIdIn(tagIds);
        List<ProductTag> productTags = tags.stream().map(tag -> new ProductTag(product, tag)).toList();
        productTagRepository.saveAll(productTags);
        product.addProductTags(productTags);

    }

    private void saveProductCategories(Product product, List<CategoryDto> categoryDto) {
        List<Long> categoryIds = categoryDto.stream().map(CategoryDto::getCategoryId).toList();
        List<Category> categories = categoryRepository.findByIdIn(categoryIds);
        List<ProductCategory> productCategories = categories.stream()
                .map(category -> new ProductCategory(product, category))
                .toList();

        productCategoryRepository.saveAll(productCategories);
        product.addProductCategories(productCategories);
    }

    private void updateProductSeries(Product product, UpdateProductDto dto) {
        product.clearProductSeries();
        product.addProductOptionGroups(dto.getOptionGroups().stream().map(mapper::toProductOptionGroup).toList());
        product.addProductImages(dto.getImages().stream().map(mapper::toProductImage).toList());
        saveProductCategories(product, dto.getCategories());
        saveProductTags(product, dto.getTags());
    }

    private Product findEntityGraphById(Long productId) {
        return productRepository.findEntityGraphById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", String.valueOf(productId)));
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", String.valueOf(productId)));
    }

    private Brand findBrand(Long dto, Long dto1) {
        return brandRepository.findById(dto)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", String.valueOf(dto1)));
    }

    private Seller findSeller(Long dto) {
        return sellerRepository.findById(dto)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", String.valueOf(dto)));
    }

    private ProductOptionGroup findOptionGroup(Long optionGroupID) {
        return productOptionGroupJapRepository.findById(optionGroupID)
                .orElseThrow(() -> new ResourceNotFoundException("OptionGroup", String.valueOf(optionGroupID)));
    }
}
