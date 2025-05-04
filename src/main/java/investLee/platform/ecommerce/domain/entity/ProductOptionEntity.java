package investLee.platform.ecommerce.domain.entity;

import investLee.platform.ecommerce.dto.request.ProductOptionUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_options")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal additionalPrice;

    private String sku;

    private int stock;

    private int displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroupEntity optionGroup;

    public void update(ProductOptionUpdateRequest dto) {
        this.name = dto.getName();
        this.additionalPrice = dto.getAdditionalPrice();
        this.sku = dto.getSku();
        this.stock = dto.getStock();
        this.displayOrder = dto.getDisplayOrder();
    }
}