package minseok.cqrschallenge.product.entity;

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
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_option_groups")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String name;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();

    @Builder
    public ProductOptionGroup(Product product, String name, Integer displayOrder) {
        this.product = product;
        this.name = name;
        this.displayOrder = displayOrder;
    }

    public void update(Product product, String name, Integer displayOrder) {
        this.product = product;
        this.name = name;
        this.displayOrder = displayOrder;
    }

    public void associateProduct(Product product) {
        this.product = product;
    }
}