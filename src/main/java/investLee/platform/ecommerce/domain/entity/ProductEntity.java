package investLee.platform.ecommerce.domain.entity;

import investLee.platform.ecommerce.domain.BaseEntity;
import investLee.platform.ecommerce.domain.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String slug;

    private String shortDescription;

    private String fullDescription;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private SellerEntity seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductCategoryEntity> productCategories = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductImageEntity> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductOptionGroupEntity> optionGroups = new ArrayList<>();

    public void updateBasicInfo(String name, String slug, String shortDesc,
                                String fullDesc, ProductStatus status,
                                SellerEntity seller, BrandEntity brand) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDesc;
        this.fullDescription = fullDesc;
        this.status = status;
        this.seller = seller;
        this.brand = brand;
    }

    public void changeStatus(ProductStatus status) {
        this.status = status;
    }
}

