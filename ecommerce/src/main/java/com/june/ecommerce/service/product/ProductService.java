package com.june.ecommerce.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.june.ecommerce.domain.brand.Brand;
import com.june.ecommerce.domain.category.Category;
import com.june.ecommerce.domain.product.Product;
import com.june.ecommerce.domain.productcategory.ProductCategory;
import com.june.ecommerce.domain.productdetail.ProductDetail;
import com.june.ecommerce.domain.productimage.ProductImage;
import com.june.ecommerce.domain.productoption.ProductOption;
import com.june.ecommerce.domain.productoptiongroup.ProductOptionGroup;
import com.june.ecommerce.domain.productprice.ProductPrice;
import com.june.ecommerce.domain.producttag.ProductTag;
import com.june.ecommerce.domain.review.Review;
import com.june.ecommerce.domain.seller.Seller;
import com.june.ecommerce.domain.tag.Tag;
import com.june.ecommerce.dto.category.CategoryDto;
import com.june.ecommerce.dto.category.ProductCategoryDto;
import com.june.ecommerce.dto.image.ImageDto;
import com.june.ecommerce.dto.image.ProductImageDto;
import com.june.ecommerce.dto.option.ProductOptionGroupDto;
import com.june.ecommerce.dto.product.*;
import com.june.ecommerce.dto.product.create.*;
import com.june.ecommerce.dto.rating.RatingDto;
import com.june.ecommerce.global.error.ErrorCode;
import com.june.ecommerce.global.exception.BusinessException;
import com.june.ecommerce.repository.brand.BrandRepository;
import com.june.ecommerce.repository.category.CategoryRepository;
import com.june.ecommerce.repository.category.ProductCategoryRepository;
import com.june.ecommerce.repository.product.*;
import com.june.ecommerce.repository.review.ReviewRepository;
import com.june.ecommerce.repository.seller.SellerRepository;
import com.june.ecommerce.repository.tag.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductPriceRepository productPriceRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductTagRepository productTagRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    private final ProductOptionGroupService productOptionGroupService;

    /**
     * 상품 등록
     * @param dto
     * @return
     */
    @Transactional
    public Product createProduct(ProductCreateDto dto) {

        // 상품 기본 정보 등록
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_INPUT, "상품 등록에 실패했습니다."));

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_INPUT, "상품 등록에 실패했습니다."));

        Product product = Product.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .seller(seller)
                .brand(brand)
                .status(dto.getStatus())
                .build();

        // 상품 상세 정보 등록
        ObjectMapper mapper = new ObjectMapper();
        String dimensionsJson = "";
        String additionalInfoJson = "";

        try {
            dimensionsJson = mapper.writeValueAsString(dto.getDetail().getDimensions());
            additionalInfoJson = mapper.writeValueAsString(dto.getDetail().getAdditionalInfo());
        } catch (JsonProcessingException e) {
            new BusinessException(ErrorCode.INVALID_INPUT, "상품 등록에 실패했습니다.");
        }

        ProductDetail detail = ProductDetail.builder()
                .product(product)
                .weight(dto.getDetail().getWeight())
                .dimensions(dimensionsJson)
                .materials(dto.getDetail().getMaterials())
                .countryOfOrigin(dto.getDetail().getCountryOfOrigin())
                .warrantyInfo(dto.getDetail().getWarrantyInfo())
                .careInstructions(dto.getDetail().getCareInstructions())
                .additionalInfo(additionalInfoJson)
                .build();
        productDetailRepository.save(detail);

        // 가격 정보 등록
        ProductPrice price = ProductPrice.builder()
                .product(product)
                .basePrice(BigDecimal.valueOf(dto.getPrice().getBasePrice()))
                .salePrice(BigDecimal.valueOf(dto.getPrice().getSalePrice()))
                .costPrice(BigDecimal.valueOf(dto.getPrice().getCostPrice()))
                .currency(dto.getPrice().getCurrency())
                .taxRate(BigDecimal.valueOf(dto.getPrice().getTaxRate()))
                .build();
        productPriceRepository.save(price);

        // 카테고리 매핑 등록
        for (ProductCategoryCreateDto categoryDto : dto.getCategories()) {
            Category category = categoryRepository.findById(categoryDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

            ProductCategory mapping = ProductCategory.builder()
                    .product(product)
                    .category(category)
                    .isPrimary(categoryDto.isPrimary())
                    .build();
            productCategoryRepository.save(mapping);
        }


        // 옵션 그룹, 옵션 등록
        for (ProductOptionGroupCreateDto groupDto : dto.getOptionGroups()) {
            ProductOptionGroup group = ProductOptionGroup.builder()
                    .product(product)
                    .name(groupDto.getName())
                    .displayOrder(groupDto.getDisplayOrder())
                    .build();
            productOptionGroupRepository.save(group);

            for (ProductOptionCreateDto optionDto : groupDto.getOptions()) {
                ProductOption option = ProductOption.builder()
                        .group(group)
                        .name(optionDto.getName())
                        .additionalPrice(optionDto.getAdditionalPrice())
                        .sku(optionDto.getSku())
                        .stock(optionDto.getStock())
                        .displayOrder(optionDto.getDisplayOrder())
                        .build();
                productOptionRepository.save(option);
                }

            }


        // 이미지 등록
        for (ProductImageCreateDto imageDto : dto.getImages()) {

            ProductOption option = null;
            if(imageDto.getOptionId() != null) {
                option = productOptionRepository.findById(imageDto.getOptionId())
                        .orElseThrow(() -> new IllegalArgumentException("옵션을 찾을 수 없습니다."));
            }

            ProductImage image = ProductImage.builder()
                    .product(product)
                    .url(imageDto.getUrl())
                    .altText(imageDto.getAltText())
                    .isPrimary(imageDto.isPrimary())
                    .displayOrder(imageDto.getDisplayOrder())
                    .productOption(option)  // nullable
                    .build();
            productImageRepository.save(image);
        }


        // 태그 매핑 등록
        for (Integer tagId : dto.getTags()) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new IllegalArgumentException("태그를 찾을 수 없습니다."));
            ProductTag productTag = ProductTag.builder()
                    .product(product)
                    .tag(tag)
                    .build();
            productTagRepository.save(productTag);
        }

        return productRepository.save(product);
    }

    /**
     * 상품 목록 조회
     * @param condition
     * @return
     */
    public List<ProductResponseDto> getProducts(ProductSearchCondition condition) {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 상품 상세 조회
     * @param productId
     * @return
     */
    public ProductDetailInfoDto getProductById(int productId) {
        // 1. 상품 기본 정보
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다"));

        // 2. 상품 상세 정보
        ProductDetail detail = productDetailRepository.findByProductId(productId)
                .orElse(null);

        // 3. 가격 정보
        ProductPrice price = productPriceRepository.findByProductId(productId)
                .orElse(null);

        // 4. 판매자, 브랜드 정보
        Seller seller = product.getSeller();
        Brand brand = product.getBrand();

        // 5. 카테고리 정보
        List<ProductCategoryDto> categories = productCategoryRepository
                .findByProductId(productId)
                .stream()
                .map(ProductCategoryDto::fromEntity)
                .collect(Collectors.toList());

        // 6. 태그 정보
        List<ProductTagDto> tags = productTagRepository
                .findByProductId(productId)
                .stream()
                .map(ProductTagDto::fromEntity)
                .collect(Collectors.toList());

        // 7. 이미지 정보
        List<ProductImageDto> images = productImageRepository
                .findByProductId(productId).stream()
                .map(ProductImageDto::fromEntity)
                .collect(Collectors.toList());

        // 8. 옵션 그룹 + 옵션
        List<ProductOptionGroupDto> optionGroups =
                productOptionGroupService.getOptionGroupsWithOptionsByProductId(productId);

        // 9. 리뷰 기반 평점 정보
        List<Review> reviews = reviewRepository.findByProductId(productId);
        RatingDto rating = RatingDto.fromReviews(reviews);

        // 10. 관련 상품 정보
        //List<RelatedProductDto> related = relatedProductService.getRelatedProducts(productId);
        List<RelatedProductDto> related = null;

        return ProductDetailInfoDto.fromEntity(
                product,
                detail,
                price,
                seller,
                brand,
                categories,
                optionGroups,
                images,
                tags,
                rating,
                related
        );
    }

    /**
     * 상품 수정
     * @param id
     * @param requestDto
     */
    public void updateProduct(int id, ProductRequestDto requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다"));

        product.updateProduct(requestDto);
        productRepository.save(product);

    }

    /**
     * 상품 삭제
     * @param id
     */
    public void deleteProduct(int id) {
        if(!productRepository.existsById(id)) {
            throw new EntityNotFoundException("상품이 존재하지 않습니다");
        }
        productRepository.deleteById(id);

    }
}
