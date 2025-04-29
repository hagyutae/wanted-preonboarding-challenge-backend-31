package com.wanted.ecommerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_options")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroup;
    private String name;

    @Column(name = "additional_price", precision = 12, scale = 2)
    private BigDecimal additionalPrice;
    private String sku;
    private Integer stock;
    @Column(name = "display_order")
    private Integer displayOrder;

    public static ProductOption of(ProductOptionGroup optionGroup, String name,
        Double additionalPrice, String sku, int stock, int displayOrder) {
        return ProductOption.builder()
            .optionGroup(optionGroup)
            .name(name)
            .additionalPrice(BigDecimal.valueOf(additionalPrice))
            .sku(sku)
            .stock(stock)
            .displayOrder(displayOrder)
            .build();
    }
}
