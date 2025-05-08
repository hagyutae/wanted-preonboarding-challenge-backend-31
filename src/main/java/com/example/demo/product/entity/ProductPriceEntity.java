package com.example.demo.product.entity;

import com.example.demo.product.controller.request.ProductPriceRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "product_prices")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    ProductEntity productEntity;
    @Column(name = "base_price", columnDefinition = "DECIMAL(12, 2)", nullable = false)
    BigDecimal basePrice;
    @Column(name = "sale_price", columnDefinition = "DECIMAL(12,2)")
    BigDecimal salePrice;
    @Column(name = "cost_price", columnDefinition = "DECIMAL(12,2)")
    BigDecimal costPrice;
    @Column(name = "currency", columnDefinition = "VARCHAR(3) DEFAULT 'KRW'")
    String currency;
    @Column(name = "tax_rate", columnDefinition = "DECIMAL(5,2)")
    BigDecimal taxRate;

    public static ProductPriceEntity create(ProductPriceRequest request) {
        return new ProductPriceEntity(
                null,
                null,
                request.basePrice(),
                request.salePrice(),
                request.costPrice(),
                request.currency(),
                request.taxRate()
        );
    }

    public void connectProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public void update(ProductPriceRequest price) {
        this.basePrice = price.basePrice();
        this.salePrice = price.salePrice();
        this.costPrice = price.costPrice();
        this.currency = price.currency();
        this.taxRate = price.taxRate();
    }
}
