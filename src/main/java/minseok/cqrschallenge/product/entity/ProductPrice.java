package minseok.cqrschallenge.product.entity;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_prices")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "cost_price")
    private BigDecimal costPrice;

    @Column(nullable = false)
    private String currency = "KRW";

    @Column(name = "tax_rate")
    private BigDecimal taxRate;

    @Builder
    public ProductPrice(Product product, BigDecimal basePrice, BigDecimal salePrice,
                        BigDecimal costPrice, String currency, BigDecimal taxRate) {
        this.product = product;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency;
        this.taxRate = taxRate;
    }

    public void update(Product product, BigDecimal basePrice, BigDecimal salePrice,
                       BigDecimal costPrice, String currency, BigDecimal taxRate) {
        this.product = product;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency;
        this.taxRate = taxRate;
    }

    public void associateProduct(Product product) {
        this.product = product;


    }
}