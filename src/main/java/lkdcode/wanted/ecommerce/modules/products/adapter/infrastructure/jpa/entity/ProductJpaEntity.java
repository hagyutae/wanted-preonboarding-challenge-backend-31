package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.framework.common.jpa.entity.EntityValidator;
import lkdcode.wanted.ecommerce.modules.brand.adapter.infrastructure.jpa.entity.BrandJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.ProductValues;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductName;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductShortDescription;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductSlug;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductStatus;
import lkdcode.wanted.ecommerce.modules.sellers.adapter.infrastructure.jpa.entity.SellerJpaEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductJpaEntity extends EntityValidator<ProductJpaEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = ProductName.LENGTH)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(max = ProductSlug.LENGTH)
    @Column(unique = true, nullable = false)
    private String slug;

    @Size(max = ProductShortDescription.LENGTH)
    @Column(name = "short_description", length = ProductShortDescription.LENGTH)
    private String shortDescription;

    @Column(name = "full_description", columnDefinition = "text")
    private String fullDescription;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private SellerJpaEntity seller;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandJpaEntity brand;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = ProductStatus.LENGTH, nullable = false)
    private ProductStatus status;

    @Builder
    public ProductJpaEntity(Long id, String name, String slug, String shortDescription, String fullDescription, SellerJpaEntity seller, BrandJpaEntity brand, ProductStatus status) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.seller = seller;
        this.brand = brand;
        this.status = status;
        super.validSelf();
    }

    public void update(final ProductValues values) {
        this.name = values.name().value();
        this.slug = values.slug().value();
        this.shortDescription = values.shortDescription().value();
        this.fullDescription = values.fullDescription().value();
        this.status = values.status();
        super.validSelf();
    }

    public void delete() {
        this.status = ProductStatus.DELETED;
    }
}