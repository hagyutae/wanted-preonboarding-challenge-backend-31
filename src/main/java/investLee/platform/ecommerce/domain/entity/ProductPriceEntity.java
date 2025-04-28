package investLee.platform.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_price")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;

    private String currency;
    private BigDecimal taxRate;

    public void update(
            BigDecimal basePrice,
            BigDecimal salePrice,
            BigDecimal costPrice,
            String currency,
            BigDecimal taxRate
    ) {
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency;
        this.taxRate = taxRate;
    }
}
