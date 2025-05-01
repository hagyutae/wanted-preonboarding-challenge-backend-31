package wanted.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "product_prices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(precision = 12, scale = 2)
    private BigDecimal salePrice;

    @Column(precision = 12, scale = 2)
    private BigDecimal costPrice;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(precision = 5, scale = 2)
    private BigDecimal taxRate;
}
