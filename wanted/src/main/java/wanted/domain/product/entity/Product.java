package wanted.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.common.entity.BaseCreateUpdateEntity;
import wanted.domain.brand.entity.Brand;
import wanted.domain.product.dto.request.ProductRequest;
import wanted.domain.review.entity.Review;
import wanted.domain.seller.entity.Seller;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseCreateUpdateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(length = 500)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String fullDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @JsonIgnore
    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE)
    private ProductDetail productDetail;

    @JsonIgnore
    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE)
    private ProductPrice productPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductCategory> productCategories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductOptionGroup> optionGroups = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductTag> tags = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Product(String name, String slug, String shortDescription, String fullDescription,
                   Status status, Seller seller, Brand brand) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.status = status;
        this.seller = seller;
        this.brand = brand;
    }

    public static Product from(ProductRequest dto, Seller seller, Brand brand) {
        return Product.builder()
                .name(dto.name())
                .slug(dto.slug())
                .shortDescription(dto.shortDescription())
                .fullDescription(dto.fullDescription())
                .status(Status.valueOf(dto.status()))
                .seller(seller)
                .brand(brand)
                .build();
    }

    public void updateFrom(ProductRequest dto, Seller seller, Brand brand) {
        this.name = dto.name();
        this.slug = dto.slug();
        this.shortDescription = dto.shortDescription();
        this.fullDescription = dto.fullDescription();
        this.status = Status.valueOf(dto.status());
        this.seller = seller;
        this.brand = brand;
    }
}
