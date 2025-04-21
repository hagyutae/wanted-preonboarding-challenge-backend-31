package investLee.platform.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;
    private String shortDescription;

    @Lob
    private String fullDescription;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private SellerEntity seller;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    private String status;

    @OneToMany(mappedBy = "product")
    private List<ProductDetailEntity> details;

    @OneToMany(mappedBy = "product")
    private List<ProductPriceEntity> prices;

    @OneToMany(mappedBy = "product")
    private List<ProductCategoryEntity> categories;

    @OneToMany(mappedBy = "product")
    private List<ProductOptionGroupEntity> optionGroups;

    @OneToMany(mappedBy = "product")
    private List<ProductImageEntity> images;

    @OneToMany(mappedBy = "product")
    private List<ProductTagEntity> tags;

    @OneToMany(mappedBy = "product")
    private List<ReviewEntity> reviews;
}

