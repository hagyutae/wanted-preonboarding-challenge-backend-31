package kr.co.wanted.backend31.common.model.product;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_options")
public class ProductOption {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "additional_price")
    private BigDecimal additionalPrice;
    @Column(name = "sku")
    private String sku;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "display_order")
    private Integer displayOrder;

    @JoinColumn(name = "option_group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOptionGroup optionGroup;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "option", cascade = CascadeType.ALL)
    private List<ProductImage> images;
}
