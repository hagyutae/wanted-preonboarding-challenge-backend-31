package com.wanted.ecommerce.product.domain;

import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.ProductPriceRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_prices")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "base_price", precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "sale_price", precision = 12, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "cost_price", precision = 12, scale = 2)
    private BigDecimal costPrice;
    private String currency;

    @Column(precision = 5, scale = 2)
    private BigDecimal taxRate;

    public static ProductPrice of(Product product, ProductPriceRequest request) {
        return ProductPrice.builder()
            .product(product)
            .basePrice(request.getBasePrice())
            .salePrice(request.getSalePrice())
            .costPrice(request.getCostPrice())
            .currency(request.getCurrency())
            .taxRate(request.getTaxRate())
            .build();
    }

    public void update(ProductPriceRequest request){
        this.basePrice = request.getBasePrice();
        this.salePrice = request.getSalePrice();
        this.costPrice = request.getCostPrice();
        this.currency = request.getCurrency();
        this.taxRate = request.getTaxRate();
    }
}
