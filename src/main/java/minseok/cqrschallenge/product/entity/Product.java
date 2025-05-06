package minseok.cqrschallenge.product.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import minseok.cqrschallenge.brand.entity.Brand;
import minseok.cqrschallenge.review.entity.Review;
import minseok.cqrschallenge.seller.entity.Seller;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
//@SQLDelete(sql = "UPDATE products SET status = 'DELETED' WHERE id = ?")
//@SQLRestriction("status != 'DELETED'")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "full_description", columnDefinition = "TEXT")
    private String fullDescription;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status = ProductStatus.ACTIVE;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductTag> tags = new ArrayList<>();

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductDetail detail;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductPrice price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionGroup> optionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @Builder(toBuilder = true)
    public Product(String name, String slug, String shortDescription, String fullDescription,
        ProductStatus status, Seller seller, Brand brand) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.status = status != null ? status : ProductStatus.ACTIVE;
        this.seller = seller;
        this.brand = brand;
    }

    public void update(String name, String slug, String shortDescription, String fullDescription,
        ProductStatus status) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.status = status;
    }

    public void delete() {
        if (this.status == ProductStatus.DELETED) {
            throw new IllegalStateException("이미 삭제된 상품입니다.");
        }

        this.status = ProductStatus.DELETED;
    }

    public boolean isDeleted() {
        return this.status == ProductStatus.DELETED;
    }


    public void addCategory(ProductCategory category) {
        categories.add(category);
        category.associateProduct(this);
    }

    public void addImage(ProductImage image) {
        images.add(image);
        image.associateProduct(this);
    }

    public void addTag(ProductTag tag) {
        tags.add(tag);
        tag.associateProduct(this);
    }

    public void setDetail(ProductDetail detail) {
        this.detail = detail;
        detail.associateProduct(this);
    }

    public void setPrice(ProductPrice price) {
        this.price = price;
        price.associateProduct(this);
    }

    public void addOptionGroup(ProductOptionGroup optionGroup) {
        optionGroups.add(optionGroup);
        optionGroup.associateProduct(this);
    }
}