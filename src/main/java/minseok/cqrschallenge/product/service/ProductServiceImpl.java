package minseok.cqrschallenge.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.brand.entity.Brand;
import minseok.cqrschallenge.brand.repository.BrandRepository;
import minseok.cqrschallenge.category.repository.CategoryRepository;
import minseok.cqrschallenge.common.dto.PaginationResponse;
import minseok.cqrschallenge.common.exception.ConflictException;
import minseok.cqrschallenge.common.exception.ResourceNotFoundException;
import minseok.cqrschallenge.product.dto.request.ProductCreateRequest;
import minseok.cqrschallenge.product.dto.request.ProductUpdateRequest;
import minseok.cqrschallenge.product.dto.response.ProductCreateResponse;
import minseok.cqrschallenge.product.dto.response.ProductDetailResponse;
import minseok.cqrschallenge.product.dto.response.ProductListResponse;
import minseok.cqrschallenge.product.dto.response.ProductUpdateResponse;
import minseok.cqrschallenge.product.entity.Product;
import minseok.cqrschallenge.product.entity.ProductCategory;
import minseok.cqrschallenge.product.entity.ProductDetail;
import minseok.cqrschallenge.product.entity.ProductImage;
import minseok.cqrschallenge.product.entity.ProductOption;
import minseok.cqrschallenge.product.entity.ProductOptionGroup;
import minseok.cqrschallenge.product.entity.ProductPrice;
import minseok.cqrschallenge.product.entity.ProductStatus;
import minseok.cqrschallenge.product.entity.ProductTag;
import minseok.cqrschallenge.product.mapper.ProductMapper;
import minseok.cqrschallenge.product.repository.ProductOptionRepository;
import minseok.cqrschallenge.product.repository.ProductRepository;
import minseok.cqrschallenge.seller.entity.Seller;
import minseok.cqrschallenge.seller.repository.SellerRepository;
import minseok.cqrschallenge.tag.entity.Tag;
import minseok.cqrschallenge.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final BrandRepository brandRepository;

    private final SellerRepository sellerRepository;

    private final CategoryRepository categoryRepository;

    private final TagRepository tagRepository;

    private final ProductOptionRepository productOptionRepository;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ProductCreateResponse createProduct(ProductCreateRequest request) {
        Product product = productMapper.toEntity(request);

        if (productRepository.existsBySlug(request.getSlug())) {
            throw new ConflictException("해당 슬러그는 이미 사용 중입니다.", "slug", request.getSlug());
        }

        if (request.getSellerId() != null) {
            Seller seller = sellerRepository.findById(request.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("판매자를 찾을 수 없습니다."));
            product.assignSeller(seller);
        }

        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("브랜드를 찾을 수 없습니다."));
            product.assignBrand(brand);
        }

        if (request.getPrice() != null) {
            product.addPrice(
                ProductPrice.builder()
                    .basePrice(request.getPrice().getBasePrice())
                    .salePrice(request.getPrice().getSalePrice())
                    .costPrice(request.getPrice().getCostPrice())
                    .currency(request.getPrice().getCurrency())
                    .taxRate(request.getPrice().getTaxRate())
                    .build()
            );
        }

        if (request.getDetail() != null) {
            product.addDetail(ProductDetail.builder()
                .product(product)
                .weight(request.getDetail().getWeight())
                .dimensions(request.getDetail().getDimensions())
                .materials(request.getDetail().getMaterials())
                .countryOfOrigin(request.getDetail().getCountryOfOrigin())
                .warrantyInfo(request.getDetail().getWarrantyInfo())
                .careInstructions(request.getDetail().getCareInstructions())
                .additionalInfo(request.getDetail().getAdditionalInfo())
                .build());
        }

        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            request.getCategories().forEach(categoryRequest ->
                product.addCategory(ProductCategory.builder()
                    .category(categoryRepository.findById(categoryRequest.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("카테고리를 찾을 수 없습니다.")))
                    .isPrimary(categoryRequest.getIsPrimary())
                    .build()));
        }
        if (request.getOptionGroups() != null && !request.getOptionGroups().isEmpty()) {
            request.getOptionGroups().forEach(optionGroupRequest -> {
                ProductOptionGroup optionGroup = ProductOptionGroup.builder()
                    .name(optionGroupRequest.getName())
                    .displayOrder(optionGroupRequest.getDisplayOrder())
                    .build();
                product.addOptionGroup(optionGroup);

                optionGroupRequest.getOptions().forEach(optionRequest -> {
                    ProductOption option = ProductOption.builder()
                        .name(optionRequest.getName())
                        .additionalPrice(optionRequest.getAdditionalPrice())
                        .sku(optionRequest.getSku())
                        .stock(optionRequest.getStock())
                        .displayOrder(optionRequest.getDisplayOrder())
                        .build();
                    optionGroup.addOption(option);
                });
            });
        }

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            request.getImages().forEach(imageRequest -> {
                ProductImage.ProductImageBuilder imageBuilder = ProductImage.builder()
                    .url(imageRequest.getUrl())
                    .altText(imageRequest.getAltText())
                    .displayOrder(imageRequest.getDisplayOrder())
                    .isPrimary(imageRequest.getIsPrimary());

                if (imageRequest.getOptionId() != null) {
                    ProductOption option = productOptionRepository.findById(
                            imageRequest.getOptionId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                            "옵션을 찾을 수 없습니다: " + imageRequest.getOptionId()));
                    imageBuilder.option(option);
                }

                ProductImage image = imageBuilder.build();
                product.addImage(image);
            });
        }

        if (request.getTags() != null && !request.getTags().isEmpty()) {
            request.getTags().forEach(tagId -> {
                Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new ResourceNotFoundException("태그를 찾을 수 없습니다."));
                ProductTag productTag = ProductTag.builder()
                    .tag(tag)
                    .build();
                product.addTag(productTag);
            });
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toCreateResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<ProductListResponse> getProducts(
        int page, int perPage, String sort, String status,
        Integer minPrice, Integer maxPrice, String category,
        Integer seller, Integer brand, Boolean inStock, String search) {

        Pageable pageable = createPageable(page, perPage, sort);

        Page<Product> productPage = productRepository.findWithFilters(
            status, minPrice, maxPrice, category, seller, brand, inStock, search, pageable);

        List<ProductListResponse> productResponses = productPage.getContent().stream()
            .map(productMapper::toListResponse)
            .collect(Collectors.toList());

        return PaginationResponse.<ProductListResponse>builder()
            .items(productResponses)
            .pagination(PaginationResponse.Pagination.builder()
                .totalItems(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .currentPage(page)
                .perPage(perPage)
                .build())
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailResponse getProductDetail(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));

        return productMapper.toDetailResponse(product);
    }

    @Override
    @Transactional
    public ProductUpdateResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));

        product.update(
            request.getName(),
            request.getSlug(),
            request.getShortDescription(),
            request.getFullDescription(),
            ProductStatus.valueOf(request.getStatus())
        );
        return productMapper.toUpdateResponse(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));
        if (product.isDeleted()) {
            throw new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다.");
        }
        product.delete();
    }

    private Pageable createPageable(int page, int perPage, String sort) {
        String[] sortParts = sort.split(":");
        String sortField = convertSortField(sortParts[0]);
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc")
            ? Sort.Direction.ASC : Sort.Direction.DESC;

        return PageRequest.of(page - 1, perPage, Sort.by(direction, sortField));
    }

    private String convertSortField(String fieldName) {
        return switch (fieldName) {
            case "created_at" -> "createdAt";
            case "updated_at" -> "updatedAt";
            default -> fieldName;
        };
    }
}
