package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.brand.domain.Brand;
import com.wanted.ecommerce.brand.dto.response.BrandDetailResponse;
import com.wanted.ecommerce.brand.dto.response.BrandResponse;
import com.wanted.ecommerce.brand.service.BrandService;
import com.wanted.ecommerce.category.dto.response.CategoryResponse;
import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductCategory;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import com.wanted.ecommerce.product.domain.ProductPrice;
import com.wanted.ecommerce.product.domain.ProductStatus;
import com.wanted.ecommerce.product.domain.ProductTag;
import com.wanted.ecommerce.product.dto.request.ProductCreateRequest;
import com.wanted.ecommerce.product.dto.request.ProductImageRequest;
import com.wanted.ecommerce.product.dto.request.ProductOptionRequest;
import com.wanted.ecommerce.product.dto.request.ProductReadAllRequest;
import com.wanted.ecommerce.product.dto.response.DetailResponse;
import com.wanted.ecommerce.product.dto.response.ProductDetailImageResponse;
import com.wanted.ecommerce.product.dto.response.ProductDetailResponse;
import com.wanted.ecommerce.product.dto.response.ProductImageCreateResponse;
import com.wanted.ecommerce.product.dto.response.ProductImageResponse;
import com.wanted.ecommerce.product.dto.response.ProductListResponse;
import com.wanted.ecommerce.product.dto.response.ProductOptionGroupResponse;
import com.wanted.ecommerce.product.dto.response.ProductOptionResponse;
import com.wanted.ecommerce.product.dto.response.ProductPriceResponse;
import com.wanted.ecommerce.product.dto.response.ProductResponse;
import com.wanted.ecommerce.product.dto.response.ProductUpdateResponse;
import com.wanted.ecommerce.product.dto.response.RelatedProductResponse;
import com.wanted.ecommerce.product.repository.ProductRepository;
import com.wanted.ecommerce.product.service.ProductCategoryService;
import com.wanted.ecommerce.product.service.ProductDetailService;
import com.wanted.ecommerce.product.service.ProductImageService;
import com.wanted.ecommerce.product.service.ProductOptionGroupService;
import com.wanted.ecommerce.product.service.ProductOptionService;
import com.wanted.ecommerce.product.service.ProductPriceService;
import com.wanted.ecommerce.product.service.ProductService;
import com.wanted.ecommerce.product.service.ProductTagService;
import com.wanted.ecommerce.review.dto.response.RatingResponse;
import com.wanted.ecommerce.review.service.ReviewService;
import com.wanted.ecommerce.seller.domain.Seller;
import com.wanted.ecommerce.seller.dto.response.SellerDetailResponse;
import com.wanted.ecommerce.seller.dto.response.SellerResponse;
import com.wanted.ecommerce.seller.service.SellerService;
import com.wanted.ecommerce.tag.domain.Tag;
import com.wanted.ecommerce.tag.dto.response.TagResponse;
import com.wanted.ecommerce.tag.service.TagService;
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
    private final ProductCategoryService productCategoryService;
    private final ProductDetailService productDetailService;
    private final ProductImageService productImageService;
    private final ProductOptionGroupService productOptionGroupService;
    private final ProductOptionService productOptionService;
    private final ProductPriceService productPriceService;
    private final ReviewService reviewService;
    private final ProductTagService productTagService;
    private final TagService tagService;
    private final SellerService sellerService;
    private final BrandService brandService;

    @Transactional
    @Override
    public ProductResponse create(ProductCreateRequest request) {
        Seller seller = sellerService.getSellerById(request.getSellerId());
        Brand brand = brandService.getBrandById(request.getBrandId());

        Product product = Product.of(
            request.getName(),
            request.getSlug(),
            request.getShortDescription(),
            request.getFullDescription(),
            seller,
            brand,
            ProductStatus.valueOf(request.getStatus())
        );

        Product saved = productRepository.save(product);

        productCategoryService.saveProductCategories(saved, request.getCategories());
        productDetailService.saveDetail(saved, request.getDetail());
        productOptionGroupService.saveProductOptionsAndGroup(saved, request.getOptionGroups());
        productImageService.createProductImages(saved, request.getImages());
        productPriceService.saveProductPrice(saved, request.getPrice());
        createProductTags(saved, request.getTags());
        return ProductResponse.of(saved.getId(), saved.getName(), saved.getSlug(),
            saved.getCreatedAt(), saved.getUpdatedAt());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductListResponse> readAll(ProductReadAllRequest request) {
        int pageNumber = Math.max(0, request.getPage() - 1);
        Pageable pageable = PageRequest.of(pageNumber, request.getPerPage());
        Page<Product> products = productRepository.findAllByRequest(request, pageable);
        return products.map(product -> {

            ProductPrice price = productPriceService.findProductPriceByProductId(product.getId());
            ProductImageResponse primaryImageResponse = productImageService.createPrimaryProductImageResponse(
                product.getId());

            double avgRating = reviewService.getAvgRatingByProductId(product.getId());

            double rating = Double.parseDouble(String.format("%.2f", avgRating));

            int reviewCount = reviewService.getReviewCountByProductId(product.getId());

            Boolean inStock = productOptionService.isExistStock(product.getId(), 0);

            BrandResponse brandResponse = brandService.createBrandResponse(
                product.getBrand().getId(),
                product.getBrand().getName());

            SellerResponse sellerResponse = sellerService.createSellerResponse(
                product.getSeller().getId(),
                product.getSeller().getName());

            return ProductListResponse.of(product.getId(), product.getName(),
                product.getShortDescription(), price.getBasePrice(), price.getSalePrice(),
                price.getCurrency(), primaryImageResponse, brandResponse, sellerResponse, rating,
                reviewCount, inStock, product.getStatus().getName(), product.getCreatedAt());
        });
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDetailResponse readDetail(long productId) {
        // product
        Product product = getProductById(productId);
        // brand
        BrandDetailResponse brandDetailResponse = brandService.createBrandDetailResponse(
            product.getBrand());
        // seller
        SellerDetailResponse sellerDetailResponse = sellerService.createSellerDetailResponse(
            product.getSeller());
        // product detail
        DetailResponse detailResponse = productDetailService.createProductDetailResponse(
            product.getDetail());
        // price
        ProductPriceResponse priceResponse = productPriceService.createPriceResponse(
            product.getPrice());
        // categories
        List<CategoryResponse> categoryResponses = productCategoryService.createCategoryResponse(
            product.getCategories());
        //option groups
        List<ProductOptionGroupResponse> optionGroupResponses = productOptionGroupService.createOptionGroupResponse(
            product.getOptionGroups());
        // images
        List<ProductDetailImageResponse> imageResponses = productImageService.createImageResponse(
            product.getImages());
        // tags
        List<TagResponse> tagResponses = createTagResponse(product);
        // rating
        RatingResponse ratingResponse = reviewService.createRatingResponse(product.getId());
        // related products
        List<RelatedProductResponse> relatedProductResponses = createRelatedProductResponse(
            product.getCategories());
        return ProductDetailResponse.of(productId, product.getName(),
            product.getSlug(), product.getShortDescription(), product.getFullDescription(),
            sellerDetailResponse, brandDetailResponse, product.getStatus().getName(),
            product.getCreatedAt(), product.getUpdatedAt(), detailResponse, priceResponse,
            categoryResponses, optionGroupResponses, imageResponses, tagResponses, ratingResponse,
            relatedProductResponses);
    }

    @Transactional
    @Override
    public ProductUpdateResponse update(long productId, ProductCreateRequest request) {
        // seller
        Seller seller = sellerService.getSellerById(request.getSellerId());
        // brand
        Brand brand = brandService.getBrandById(request.getBrandId());

        Product target = getProductById(productId);

        target.update(request.getName(), request.getSlug(), request.getShortDescription(),
            request.getFullDescription(), seller, brand,
            ProductStatus.valueOf(request.getStatus()));

        // detail
        productDetailService.updateDetail(target.getDetail(), request.getDetail());

        // price
        productPriceService.updatePrice(target.getPrice(), request.getPrice());

        //categories
        productCategoryService.updateCategories(target, request.getCategories());

        //option group, option
        productOptionGroupService.deleteProductOptionGroup(target.getId());
        productOptionGroupService.saveProductOptionsAndGroup(target, request.getOptionGroups());

        // image
        productImageService.deleteProductImageByProductId(target.getId());
        productImageService.createProductImages(target, request.getImages());

        return ProductUpdateResponse.of(target.getId(), target.getName(), target.getSlug(),
            target.getUpdatedAt());
    }

    @Transactional
    @Override
    public void delete(long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }

    @Transactional
    @Override
    public ProductOptionResponse addProductOption(long id,
        ProductOptionRequest optionRequest) {
        Product product = getProductById(id);
        ProductOptionGroup optionGroup = productOptionGroupService.updateOptionGroup(product,
            optionRequest.getOptionGroupId());
        return productOptionService.createProductOption(product, optionGroup, optionRequest);
    }

    @Override
    public ProductOptionResponse updateProductOption(long id, long optionId,
        ProductOptionRequest optionRequest) {
        Product product = getProductById(id);
        checkExistOption(product.getId(), optionId);

        return productOptionService.updateProductOption(optionId, product, optionRequest);
    }

    @Override
    public void deleteProductOption(long id, long optionId) {
        checkExistOption(id, optionId);
        productOptionService.deleteProductOption(optionId);
    }

    @Override
    public ProductImageCreateResponse addProductImage(long id, ProductImageRequest imageRequest) {
        Product product = getProductById(id);
        ProductOption option = productOptionService.findOptionById(imageRequest.getOptionId());
        return productImageService.createProductImage(product, option, imageRequest);
    }

    private List<Long> createProductTags(Product saved, List<Long> tagIds) {
        List<Tag> tags = tagIds.stream().map(tagService::getTagByTagId)
            .toList();
        List<ProductTag> savedTagList = productTagService.saveAllProductTags(saved, tags);
        return savedTagList.stream().map(ProductTag::getId).toList();
    }

    private List<TagResponse> createTagResponse(Product product) {
        return product.getTags().stream().map(productTag ->
            TagResponse.of(productTag.getId(), productTag.getTag().getName(),
                product.getSlug())).toList();
    }

    private List<RelatedProductResponse> createRelatedProductResponse(
        List<ProductCategory> categories) {
        ProductCategory primaryCategory = categories.stream()
            .filter(ProductCategory::isPrimary)
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));

        List<Product> relatedProducts = productRepository.findRelatedProductsByCategoryId(
            primaryCategory.getCategory().getId());

        return relatedProducts.stream()
            .map(relatedProduct -> {
                ProductImageResponse imageResponse = productImageService.createPrimaryProductImageResponse(
                    relatedProduct.getId());

                ProductPrice productPrice = relatedProduct.getPrice();
                return RelatedProductResponse.of(relatedProduct.getId(), relatedProduct.getName(),
                    relatedProduct.getSlug(), relatedProduct.getShortDescription(), imageResponse,
                    productPrice.getBasePrice(), productPrice.getSalePrice(),
                    productPrice.getCurrency());
            }).toList();
    }

    public Product getProductById(long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
    }

    private void checkExistOption(long productId, long optionId) {
        Product product = getProductById(productId);
        ProductOption option = productOptionService.findOptionById(optionId);
        boolean isExistOption = product.getOptionGroups().stream()
            .anyMatch(optionGroup -> optionGroup.getId().equals(option.getOptionGroup().getId()));
        if (!isExistOption) {
            throw new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND);
        }
    }
}
