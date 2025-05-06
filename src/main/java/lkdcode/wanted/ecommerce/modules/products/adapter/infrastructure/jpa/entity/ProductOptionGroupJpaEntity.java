package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product_option_groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionGroupJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;

    private String name;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Builder
    public ProductOptionGroupJpaEntity(Long id, ProductJpaEntity product, String name, Integer displayOrder) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.displayOrder = displayOrder;
    }
}