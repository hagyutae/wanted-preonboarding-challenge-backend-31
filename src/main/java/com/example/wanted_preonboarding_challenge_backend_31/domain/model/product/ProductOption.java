package com.example.wanted_preonboarding_challenge_backend_31.domain.model.product;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup productOptionGroup;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(precision = 12, scale = 2)
    private BigDecimal additionalPrice; // default 0

    @Column(length = 100)
    private String sku;

    private int stock;

    private int displayOrder;


    public static ProductOption create(ProductOptionGroup productOptionGroup, String name, BigDecimal additionalPrice,
                                       String sku, int stock, int displayOrder) {
        return ProductOption.builder()
                .productOptionGroup(productOptionGroup)
                .name(name)
                .additionalPrice(additionalPrice == null ? BigDecimal.ZERO : additionalPrice)
                .sku(sku)
                .stock(stock)
                .displayOrder(displayOrder)
                .build();
    }
}
