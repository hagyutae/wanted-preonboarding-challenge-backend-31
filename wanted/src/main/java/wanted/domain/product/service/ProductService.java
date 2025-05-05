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
import wanted.domain.product.dto.Pagination;
import wanted.domain.product.dto.ProductCategoryRequest;
import wanted.domain.product.dto.ProductCreateRequest;
import wanted.domain.product.dto.ProductCreateResponse;
import wanted.domain.product.dto.ProductImageRequest;
import wanted.domain.product.dto.ProductListResponse;
import wanted.domain.product.dto.ProductOptionGroupRequest;
import wanted.domain.product.dto.ProductOptionRequest;
import wanted.domain.product.dto.ProductSearchCondition;
import wanted.domain.product.dto.SimpleProductResponse;
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


    /*
1. Product                (먼저 저장 → 기본 ID 생성됨)
2. ProductDetail          (1:1)
3. ProductPrice           (1:1)
4. ProductCategory        (1:N)
5. ProductOptionGroup     (1:N)
6. ProductOption          (N:1)
7. ProductImage           (1:N, option FK nullable)
8. ProductTag             (1:N)
 */
    @Transactional
    public ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest){
        Seller seller = sellerRepository.findById(productCreateRequest.sellerId())
                .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Seller", productCreateRequest.sellerId())));

        Brand brand = brandRepository.findById(productCreateRequest.brandId())
                .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Brand", productCreateRequest.brandId())));

        Product product = Product.from(productCreateRequest, seller, brand);
        productRepository.save(product);

        ProductDetail productDetail = ProductDetail.from(productCreateRequest.detail(), product);
        productDetailRepository.save(productDetail);

        ProductPrice productPrice = ProductPrice.from(productCreateRequest.price(), product);
        productPriceRepository.save(productPrice);

        saveProductCategory(productCreateRequest, product);
        saveProductOption(productCreateRequest, product);
        saveProductImage(productCreateRequest, product);
        saveProductTag(productCreateRequest, product);

        return ProductCreateResponse.of(product);
    }

    private void saveProductCategory(ProductCreateRequest productCreateRequest, Product product) {
        for (ProductCategoryRequest productCategoryRequest : productCreateRequest.categories()) {
            Category category = categoryRepository.findById(productCategoryRequest.categoryId())
                    .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Category", productCategoryRequest.categoryId())));
            ProductCategory productCategory = ProductCategory.from(productCategoryRequest, product, category);
            productCategoryRepository.save(productCategory);
        }
    }

    private void saveProductOption(ProductCreateRequest productCreateRequest, Product product) {
        for (ProductOptionGroupRequest productOptionGroupRequest : productCreateRequest.optionGroups()) {
            ProductOptionGroup productOptionGroup = ProductOptionGroup.from(productOptionGroupRequest, product);
            productOptionGroupRepository.save(productOptionGroup);

            for (ProductOptionRequest productOptionRequest : productOptionGroupRequest.options()) {
                ProductOption productOption = ProductOption.from(productOptionRequest, productOptionGroup);
                productOptionRepository.save(productOption);
            }
        }
    }

    private void saveProductImage(ProductCreateRequest productCreateRequest, Product product) {
        if (productCreateRequest.images() == null) return;
        for (ProductImageRequest productImageRequest : productCreateRequest.images()) {
            ProductOption productOption = null;

            if (productImageRequest.optionId() != null) {
                productOption = productOptionRepository.findById(productImageRequest.optionId())
                        .orElseThrow(() -> new CustomException(GlobalExceptionCode.RESOURCE_NOT_FOUND, resourceNotFoundDetails("Option", productImageRequest.optionId())));
            }
            ProductImage productImage = ProductImage.from(productImageRequest, product, productOption);
            productImageRepository.save(productImage);
        }
    }

    private void saveProductTag(ProductCreateRequest productCreateRequest, Product product) {
        if (productCreateRequest.tags() == null) return;
        for (Long tagId : productCreateRequest.tags()) {
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
