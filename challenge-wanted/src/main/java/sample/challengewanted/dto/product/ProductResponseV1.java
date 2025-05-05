package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.domain.product.entity.Product;
import sample.challengewanted.dto.option.OptionGroupResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductResponseV1 {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private SellerResponse seller;
    private BrandResponse brand;
    private ProductDetailResponse detail;
    private ProductPriceResponse price;
    private List<ProductCategoryDto> categories;
    private List<OptionGroupResponse> optionGroups;
    private List<ImageResponse> images;
    private List<TagResponse> tags;

    private RatingDto rating;
    private List<RelatedProductDto> relatedProducts;

    @QueryProjection
    public ProductResponseV1(Long id, String name, String slug, String shortDescription, String fullDescription, String status, LocalDateTime createdAt, LocalDateTime updatedAt, SellerResponse seller, BrandResponse brand, ProductDetailResponse detail, ProductPriceResponse price ) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.seller = seller;
        this.brand = brand;
        this.detail = detail;
        this.price = price;
    }

    @QueryProjection
    public ProductResponseV1(Long id, String name, String slug, String shortDescription, String fullDescription, String status, LocalDateTime createdAt, LocalDateTime updatedAt, SellerResponse seller, BrandResponse brand, ProductDetailResponse detail, ProductPriceResponse price, List<ProductCategoryDto> categories, List<OptionGroupResponse> optionGroups, List<ImageResponse> images, List<TagResponse> tags, RatingDto rating, List<RelatedProductDto> relatedProducts) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.seller = seller;
        this.brand = brand;
        this.detail = detail;
        this.price = price;
        this.categories = categories;
        this.optionGroups = optionGroups;
        this.images = images;
        this.tags = tags;
        this.rating = rating;
        this.relatedProducts = relatedProducts;
    }
}
