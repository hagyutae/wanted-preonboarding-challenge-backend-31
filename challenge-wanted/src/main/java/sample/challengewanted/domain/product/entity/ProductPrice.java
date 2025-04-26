package sample.challengewanted.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_prices")
@Entity
public class ProductPrice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private String currency;
    private BigDecimal taxRate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;
}
