package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lkdcode.wanted.ecommerce.framework.common.jpa.entity.EntityValidator;
import lkdcode.wanted.ecommerce.modules.category.adapter.infrastructure.jpa.entity.CategoryJpaEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategoryJpaEntity extends EntityValidator<ProductCategoryJpaEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductJpaEntity product;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryJpaEntity category;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @Builder
    public ProductCategoryJpaEntity(Long id, ProductJpaEntity product, CategoryJpaEntity category, Boolean isPrimary) {
        this.id = id;
        this.product = product;
        this.category = category;
        this.isPrimary = isPrimary;
        super.validSelf();
    }
}