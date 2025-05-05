package wanted.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.common.exception.CustomException;
import wanted.common.exception.code.GlobalExceptionCode;
import wanted.domain.brand.entity.Brand;
import wanted.domain.brand.repository.BrandRepository;
import wanted.domain.category.entity.Category;
import wanted.domain.category.repository.CategoryRepository;
import wanted.domain.product.dto.response.Pagination;
import wanted.domain.product.dto.request.ProductCategoryRequest;
import wanted.domain.product.dto.request.ProductRequest;
import wanted.domain.product.dto.response.ProductCreateResponse;
import wanted.domain.product.dto.request.ProductImageRequest;
import wanted.domain.product.dto.response.ProductDetailResponse;
import wanted.domain.product.dto.response.ProductListResponse;
import wanted.domain.product.dto.request.ProductOptionGroupRequest;
import wanted.domain.product.dto.request.ProductOptionRequest;
import wanted.domain.product.dto.ProductSearchCondition;
import wanted.domain.product.dto.response.ProductRatingResponse;
import wanted.domain.product.dto.response.SimpleProductResponse;
import wanted.domain.product.entity.Product;
import wanted.domain.product.entity.ProductCategory;
import wanted.domain.product.entity.ProductDetail;
import wanted.domain.product.entity.ProductImage;
import wanted.domain.product.entity.ProductOption;
import wanted.domain.product.entity.ProductOptionGroup;
import wanted.domain.product.entity.ProductPrice;
import wanted.domain.product.entity.ProductTag;
import wanted.domain.product.repository.ProductCategoryRepository;
import wanted.domain.product.repository.ProductDetailRepository;
import wanted.domain.product.repository.ProductImageRepository;
import wanted.domain.product.repository.ProductOptionGroupRepository;
import wanted.domain.product.repository.ProductOptionRepository;
import wanted.domain.product.repository.ProductPriceRepository;
import wanted.domain.product.repository.ProductQueryRepository;
import wanted.domain.product.repository.ProductRepository;
import wanted.domain.product.repository.ProductTagRepository;
import wanted.domain.review.service.ReviewService;
import wanted.domain.seller.entity.Seller;
import wanted.domain.seller.repository.SellerRepository;
import wanted.domain.tag.entity.Tag;
import wanted.domain.tag.repository.TagRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductPriceRepository productPriceRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;
    private final ProductQueryRepository productQueryRepository;
    private final ReviewService reviewService;

    @Transactional
    public ProductCreateResponse createProduct(ProductRequest productRequest){
        Seller seller = sellerRepository.findById(productRequest.sellerId())
                .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Seller", productRequest.sellerId())));

        Brand brand = brandRepository.findById(productRequest.brandId())
                .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Brand", productRequest.brandId())));

        Product product = Product.from(productRequest, seller, brand);
        productRepository.save(product);

        ProductDetail productDetail = ProductDetail.from(productRequest.detail(), product);
        productDetailRepository.save(productDetail);

        ProductPrice productPrice = ProductPrice.from(productRequest.price(), product);
        productPriceRepository.save(productPrice);

        saveProductCategory(productRequest, product);
        saveProductOption(productRequest, product);
        saveProductImage(productRequest, product);
        saveProductTag(productRequest, product);

        return ProductCreateResponse.of(product);
    }

    @Transactional
    public ProductCreateResponse updateProduct(Long productId, ProductRequest request) {
        Seller seller = sellerRepository.findById(request.sellerId())
                .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Seller", request.sellerId())));

        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Brand", request.brandId())));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Product", productId)));

        productDetailRepository.deleteByProductId(productId);
        productPriceRepository.deleteByProductId(productId);
        productCategoryRepository.deleteByProductId(productId);
        productOptionGroupRepository.deleteByProductId(productId);
        productImageRepository.deleteByProductId(productId);
        productTagRepository.deleteByProductId(productId);

        product.updateFrom(request, seller, brand);

        productDetailRepository.save(ProductDetail.from(request.detail(), product));
        productPriceRepository.save(ProductPrice.from(request.price(), product));

        saveProductCategory(request, product);
        saveProductOption(request, product);
        saveProductImage(request, product);
        saveProductTag(request, product);

        return ProductCreateResponse.of(product);
    }

    private void saveProductCategory(ProductRequest productRequest, Product product) {
        for (ProductCategoryRequest productCategoryRequest : productRequest.categories()) {
            Category category = categoryRepository.findById(productCategoryRequest.categoryId())
                    .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Category", productCategoryRequest.categoryId())));
            ProductCategory productCategory = ProductCategory.from(productCategoryRequest, product, category);
            productCategoryRepository.save(productCategory);
        }
    }

    private void saveProductOption(ProductRequest productRequest, Product product) {
        for (ProductOptionGroupRequest productOptionGroupRequest : productRequest.optionGroups()) {
            ProductOptionGroup productOptionGroup = ProductOptionGroup.from(productOptionGroupRequest, product);
            productOptionGroupRepository.save(productOptionGroup);

            for (ProductOptionRequest productOptionRequest : productOptionGroupRequest.options()) {
                ProductOption productOption = ProductOption.from(productOptionRequest, productOptionGroup);
                productOptionRepository.save(productOption);
            }
        }
    }

    private void saveProductImage(ProductRequest productRequest, Product product) {
        if (productRequest.images() == null) return;
        for (ProductImageRequest productImageRequest : productRequest.images()) {
            ProductOption productOption = null;

            if (productImageRequest.optionId() != null) {
                productOption = productOptionRepository.findById(productImageRequest.optionId())
                        .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Option", productImageRequest.optionId())));
            }
            ProductImage productImage = ProductImage.from(productImageRequest, product, productOption);
            productImageRepository.save(productImage);
        }
    }

    private void saveProductTag(ProductRequest productRequest, Product product) {
        if (productRequest.tags() == null) return;
        for (Long tagId : productRequest.tags()) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Tag", tagId)));

            ProductTag productTag = ProductTag.from(product, tag);
            productTagRepository.save(productTag);
        }
    }

    @Transactional(readOnly = true)
    public ProductListResponse getFilteredProducts(ProductSearchCondition condition) {
        int page = Optional.ofNullable(condition.page()).orElse(1) - 1;
        int perPage = Optional.ofNullable(condition.perPage()).orElse(10);
        Pageable pageable = PageRequest.of(page, perPage, parseSort(condition.sort()));

        Page<Product> result = productQueryRepository.search(condition, pageable);
        List<SimpleProductResponse> items = result.getContent().stream()
                .map(SimpleProductResponse::from)
                .toList();

        return new ProductListResponse(
                items,
                new Pagination(result.getTotalElements(), result.getTotalPages(), result.getNumber() + 1, result.getSize())
        );
    }

    @Transactional(readOnly = true)
    public ProductDetailResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Product", productId)));

        ProductRatingResponse rating = reviewService.getProductRating(productId);
        List<Product> relatedProducts = productRepository.findRelatedProducts(productId);

        return ProductDetailResponse.of(product, rating, relatedProducts);
    }

    private Sort parseSort(String sortString) {
        if (sortString == null || sortString.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        return Sort.by(
                Arrays.stream(sortString.split(","))
                        .map(s -> {
                            String[] parts = s.split(":");
                            String field = parts[0];
                            Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("asc"))
                                    ? Sort.Direction.ASC : Sort.Direction.DESC;
                            return new Sort.Order(direction, field);
                        }).toList()
        );
    }


    private Map<String, Object> resourceNotFoundDetails(String type, Object id) {
        return Map.of("resourceType", type, "resourceId", id);
    }
}
