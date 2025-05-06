package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.framework.common.jpa.entity.EntityValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.model.option.UpdateOptionModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "product_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionJpaEntity extends EntityValidator<ProductOptionJpaEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroupJpaEntity optionGroup;

    @NotNull
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @Digits(integer = 10, fraction = 2)
    @Column(name = "additional_price", precision = 12, scale = 2)
    private BigDecimal additionalPrice = BigDecimal.ZERO;

    @NotNull
    @Size(max = 100)
    @Column(length = 100)
    private String sku;

    @Column
    private Integer stock = 0;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Builder
    public ProductOptionJpaEntity(Long id, ProductOptionGroupJpaEntity optionGroup, String name, BigDecimal additionalPrice, String sku, Integer stock, Integer displayOrder) {
        this.id = id;
        this.optionGroup = optionGroup;
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.sku = sku;
        this.stock = stock;
        this.displayOrder = displayOrder;
        super.validSelf();
    }

    public void update(UpdateOptionModel model) {
        this.name = model.name().value();
        this.additionalPrice = model.additionalPrice().value();
        this.sku = model.sku().value();
        this.stock = model.stock().value();
        this.displayOrder = model.displayOrder().value();
        super.validSelf();
    }
}