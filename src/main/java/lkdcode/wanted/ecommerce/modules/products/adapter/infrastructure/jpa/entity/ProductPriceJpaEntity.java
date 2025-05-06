package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.framework.common.jpa.entity.EntityValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.ProductPriceModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "product_prices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPriceJpaEntity extends EntityValidator<ProductPriceJpaEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductJpaEntity product;

    @Digits(integer = 10, fraction = 2)
    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Digits(integer = 10, fraction = 2)
    @Column(name = "sale_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal salePrice;

    @Digits(integer = 10, fraction = 2)
    @Column(name = "cost_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal costPrice;

    @Size(max = 3)
    @Column(length = 3)
    private String currency = "KRW";

    @Digits(integer = 3, fraction = 2)
    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;

    @Builder
    public ProductPriceJpaEntity(Long id, ProductJpaEntity product, BigDecimal basePrice, BigDecimal salePrice, BigDecimal costPrice, String currency, BigDecimal taxRate) {
        this.id = id;
        this.product = product;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency;
        this.taxRate = taxRate;
        super.validSelf();
    }

    public void update(final ProductPriceModel model) {
        this.basePrice = model.basePrice().value();
        this.salePrice = model.salePrice().value();
        this.costPrice = model.costPrice().value();
        this.currency = model.currency().value();
        this.taxRate = model.taxRate().value();
        super.validSelf();
    }
}