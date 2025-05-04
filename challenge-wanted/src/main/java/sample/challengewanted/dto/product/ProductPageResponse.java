package sample.challengewanted.dto.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductPageResponse {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private String currency;
    private ImageResponse primaryImage;
    private BrandResponse brand;
    private SellerResponse seller;
    private Double rating;
    private Long reviewCount;
    private Boolean inStock;
    private String status;
    private LocalDateTime createdAt;

    @QueryProjection
    public ProductPageResponse(Long id,
                               String name,
                               String slug,
                               String shortDescription,
                               BigDecimal basePrice,
                               BigDecimal salePrice,
                               String currency,
                               ImageResponse primaryImage,
                               BrandResponse brand,
                               SellerResponse seller,
                               Double rating,
                               Long reviewCount,
                               Integer inStock,
                               String status,
                               LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.currency = currency;
        this.primaryImage = primaryImage;
        this.brand = brand;
        this.seller = seller;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.inStock = inStock >= 1;
        this.status = status;
        this.createdAt = createdAt;
    }

}