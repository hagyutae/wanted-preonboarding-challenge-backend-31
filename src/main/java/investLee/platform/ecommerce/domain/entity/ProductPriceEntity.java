package investLee.platform.ecommerce.domain.entity;

import investLee.platform.ecommerce.dto.request.ProductUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_prices")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPriceEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;

    private String currency;

    private BigDecimal taxRate;

    public void updatePrice(ProductUpdateRequest.ProductPriceDTO dto) {
        this.basePrice = dto.getBasePrice();
        this.salePrice = dto.getSalePrice();
        this.costPrice = dto.getCostPrice();
        this.currency = dto.getCurrency();
        this.taxRate = dto.getTaxRate();
    }
}
