package com.sandro.wanted_shop.product.entity;

import com.sandro.wanted_shop.common.entity.BaseEntity;
import com.sandro.wanted_shop.product.entity.enums.Currency;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Table(name = "product_prices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPrice extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private BigDecimal basePrice;

    private BigDecimal salePrice;

    private BigDecimal costPrice;

    @Column(length = 3)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal taxRate;

    @Builder
    public ProductPrice(Product product, BigDecimal basePrice, BigDecimal salePrice, BigDecimal costPrice, Currency currency, BigDecimal taxRate) {
        this.product = product;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = Optional.ofNullable(currency).orElse(Currency.KRW);
        this.taxRate = taxRate;

        validate();

        this.product.updatePrice(this);
    }

    private void validate() {
        assert this.product != null
                && this.basePrice != null
                : "Product and basePrice cannot be null";
    }
}