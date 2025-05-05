package com.june.ecommerce.service.product;

import com.june.ecommerce.domain.brand.Brand;
import com.june.ecommerce.domain.product.Product;
import com.june.ecommerce.domain.productdetail.ProductDetail;
import com.june.ecommerce.domain.productprice.ProductPrice;
import com.june.ecommerce.domain.review.Review;
import com.june.ecommerce.domain.seller.Seller;
import com.june.ecommerce.dto.category.CategoryDto;
import com.june.ecommerce.dto.category.ProductCategoryDto;
import com.june.ecommerce.dto.image.ImageDto;
import com.june.ecommerce.dto.option.ProductOptionGroupDto;
import com.june.ecommerce.dto.product.*;
import com.june.ecommerce.dto.rating.RatingDto;
import com.june.ecommerce.repository.brand.BrandRepository;
import com.june.ecommerce.repository.category.ProductCategoryRepository;
import com.june.ecommerce.repository.product.ProductDetailRepository;
import com.june.ecommerce.repository.product.ProductPriceRepository;
import com.june.ecommerce.repository.product.ProductRepository;
import com.june.ecommerce.repository.product.ProductTagRepository;
import com.june.ecommerce.repository.review.ReviewRepository;
import com.june.ecommerce.repository.seller.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductPriceRepository productPriceRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductTagRepository productTagRepository;
    private final SellerRepository sellerRepository;
    private final BrandRepository brandRepository;
    private final ReviewRepository reviewRepository;

    private final RelatedProductService relatedProductService;

    public Product createProduct(ProductRequestDto dto) {

        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("판매자를 찾을 수 없습니다."));

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));


        Product product = Product.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .seller(seller)
                .brand(brand)
                .status(dto.getStatus())
                .build();

        return productRepository.save(product);
    }

    public List<ProductResponseDto> getProducts(ProductSearchCondition condition) {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

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
        List<ImageDto> images = productImageRepository
                .findByProductId(productId).stream()
                .map(ImageDto::fromEntity)
                .collecet(Collectors.toList());

        // 8. 옵션 그룹 + 옵션
        List<ProductOptionGroupDto> optionGroups =
                productOptionGroupServie.getOptionGroupsWithOptionsByProductId(productId);

        // 9. 리뷰 기반 평점 정보
        List<Review> reviews = reviewRepository.findByProductId(productId);
        RatingDto rating = RatingDto.fromReviews(reviews);

        // 10. 관련 상품 정보
        List<RelatedProductDto> related = relatedProductService.getRelatedProducts(productId);

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

    public void updateProduct(int id, ProductRequestDto requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다"));

        product.updateProduct(requestDto);
        productRepository.save(product);

    }

    public void deleteProduct(int id) {
        if(!productRepository.existsById(id)) {
            throw new EntityNotFoundException("상품이 존재하지 않습니다");
        }
        productRepository.deleteById(id);

    }
}
