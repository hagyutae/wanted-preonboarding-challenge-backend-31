package wanted.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import wanted.domain.product.dto.ProductOptionRequest;

import java.math.BigDecimal;

@Entity(name = "product_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroup;

    @Column(nullable = false)
    private String name;

    @Column(precision = 12, scale = 2)
    private BigDecimal additionalPrice;

    @Column(length = 100)
    private String sku;

    private int stock;

    private int displayOrder;

    @Builder
    public ProductOption(ProductOptionGroup optionGroup, String name, BigDecimal additionalPrice,
                         String sku, int stock, int displayOrder) {
        this.optionGroup = optionGroup;
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.sku = sku;
        this.stock = stock;
        this.displayOrder = displayOrder;
    }

    public static ProductOption from(ProductOptionRequest dto, ProductOptionGroup optionGroup) {
        return ProductOption.builder()
                .optionGroup(optionGroup)
                .name(dto.name())
                .additionalPrice(dto.additionalPrice())
                .sku(dto.sku())
                .stock(dto.stock())
                .displayOrder(dto.displayOrder())
                .build();
    }
}
