package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.framework.common.jpa.entity.EntityValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductAltText;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrder;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageIsPrimary;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageUrl;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product_images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImageJpaEntity extends EntityValidator<ProductImageJpaEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;

    @NotNull()
    @Size(max = ProductImageUrl.LENGTH)
    @Column(nullable = false)
    private String url;

    @Size(max = ProductAltText.LENGTH)
    @Column(name = "alt_text")
    private String altText;

    @Column(name = "is_primary")
    private Boolean isPrimary = ProductImageIsPrimary.DEFAULT;

    @Column(name = "display_order")
    private Integer displayOrder = ProductImageDisplayOrder.DEFAULT;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private ProductOptionJpaEntity option;

    @Builder
    public ProductImageJpaEntity(Long id, ProductJpaEntity product, String url, String altText, Boolean isPrimary, Integer displayOrder, ProductOptionJpaEntity option) {
        this.id = id;
        this.product = product;
        this.url = url;
        this.altText = altText;
        this.isPrimary = isPrimary;
        this.displayOrder = displayOrder;
        this.option = option;
        super.validSelf();
    }

    public void primaryToggle() {
        this.isPrimary = !this.isPrimary;
    }

    public void incrementOrder() {
        this.displayOrder++;
    }

    public void decrementOrder() {
        this.displayOrder--;
    }
}