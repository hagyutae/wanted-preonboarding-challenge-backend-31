package com.june.ecommerce.domain.productprice;

import com.june.ecommerce.domain.product.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Getter
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private String currency = "KRW";
    private BigDecimal taxRate;

    @Builder
    public ProductPrice(int id, Product product, BigDecimal basePrice, BigDecimal salePrice, BigDecimal costPrice, String currency, BigDecimal taxRate) {
        this.id = id;
        this.product = product;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency;
        this.taxRate = BigDecimal.ZERO;
    }

    public void update(int basePrice, int salePrice, int costPrice, String currency, int taxRate) {
        Optional.ofNullable(basePrice).ifPresent(b -> this.basePrice = BigDecimal.valueOf(b));
        Optional.ofNullable(salePrice).ifPresent(s -> this.salePrice = BigDecimal.valueOf(s));
        Optional.ofNullable(costPrice).ifPresent(s -> this.costPrice = BigDecimal.valueOf(s));
        Optional.ofNullable(currency).ifPresent(c -> this.currency = currency);
        Optional.ofNullable(taxRate).ifPresent(tax -> this.taxRate = BigDecimal.valueOf(tax));
    }
}
