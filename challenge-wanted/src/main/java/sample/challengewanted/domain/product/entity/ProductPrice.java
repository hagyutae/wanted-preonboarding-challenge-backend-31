package sample.challengewanted.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.api.controller.product.request.ProductPriceRequest;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_prices")
@Entity
public class ProductPrice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "cost_price")
    private BigDecimal costPrice;

    @Column(name = "currency")
    private String currency;

    @Column(name = "tax_rate")
    private BigDecimal taxRate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    private ProductPrice(ProductPriceRequest request, Product product) {
        this.basePrice = request.getBasePrice();
        this.salePrice = request.getSalePrice();
        this.costPrice = request.getCostPrice();
        this.currency = request.getCurrency();
        this.taxRate = request.getTaxRate();
        this.product = product;
    }

    public static ProductPrice create(ProductPriceRequest request, Product product) {
        return new ProductPrice(request, product);
    }

}
