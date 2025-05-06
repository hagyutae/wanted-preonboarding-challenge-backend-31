package com.june.ecommerce.dto.product;

import com.june.ecommerce.domain.brand.Brand;
import com.june.ecommerce.domain.product.Product;
import com.june.ecommerce.domain.productdetail.ProductDetail;
import com.june.ecommerce.domain.productprice.ProductPrice;
import com.june.ecommerce.domain.seller.Seller;
import com.june.ecommerce.dto.brand.BrandDto;
import com.june.ecommerce.dto.category.ProductCategoryDto;
import com.june.ecommerce.dto.image.ProductImageDto;
import com.june.ecommerce.dto.option.ProductOptionGroupDto;
import com.june.ecommerce.dto.rating.RatingDto;
import com.june.ecommerce.dto.seller.SellerDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailInfoDto {

    private int id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private SellerDto seller;
    private BrandDto brand;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductDetailDto detail;
    private ProductPriceDto price;
    private List<ProductCategoryDto> categories;
    private List<ProductOptionGroupDto> optionGroups;
    private List<ProductImageDto> images;
    private List<ProductTagDto> tags;
    private RatingDto rating;
    private List<RelatedProductDto> relatedProducts;


    public static ProductDetailInfoDto fromEntity(
            Product product,
            ProductDetail detail,
            ProductPrice price,
            Seller seller,
            Brand brand,
            List<ProductCategoryDto> categories,
            List<ProductOptionGroupDto> optionGroups,
            List<ProductImageDto> images,
            List<ProductTagDto> tags,
            RatingDto rating,
            List<RelatedProductDto> relatedProducts
    ) {
        return ProductDetailInfoDto.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .shortDescription(product.getShortDescription())
                .fullDescription(product.getFullDescription())
                .status(product.getStatus())
                .createdAt(product.getCreateAt())
                .updatedAt(product.getUpdateAt())
                .seller(SellerDto.fromEntity(seller))
                .brand(BrandDto.fromEntity(brand))
                .detail(ProductDetailDto.fromEntity(detail))
                .price(ProductPriceDto.fromEntity(price))
                .categories(categories)
                .optionGroups(optionGroups)
                .images(images)
                .tags(tags)
                .rating(rating)
                .relatedProducts(relatedProducts)
                .build();
    }
}
