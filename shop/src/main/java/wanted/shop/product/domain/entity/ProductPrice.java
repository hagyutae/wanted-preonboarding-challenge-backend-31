package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_detail_id_seq")
    @SequenceGenerator(name = "product_detail_id_seq", sequenceName = "product_detail_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private String currency;
    private BigDecimal taxRate;
}
