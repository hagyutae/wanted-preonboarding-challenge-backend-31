package com.ecommerce.products.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Comment("상품 옵션 그룹")
@Entity
@Table(name = "product_option_groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionGroup {
    @Comment("상품 옵션 그룹 ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("상품 ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Comment("옵션 그룹 명")
    @Column(nullable = false)
    private String name;

    @Comment("표시 순서")
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductOption> options = new ArrayList<>();

    // Helper method
    public void addOption(ProductOption option) {
        option.setOptionGroup(this);
        options.add(option);
    }

}