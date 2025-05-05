package sample.challengewanted.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_option_groups")
@Entity
public class ProductOptionGroup {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer displayOrder;

    @OneToMany(mappedBy = "productOptionGroup")
    private List<ProductOption> productOptions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOption productOption;

    private ProductOptionGroup(String name, Integer displayOrder, Product product) {
        this.name = name;
        this.displayOrder = displayOrder;
        this.product = product;
    }

    public static ProductOptionGroup create(String name, Integer displayOrder, Product product) {
        return new ProductOptionGroup(name, displayOrder, product);
    }

    public void assignProduct(Product product) {
        this.product = product;
    }

    public void assignProductOption(ProductOption productOption) {
        this.productOptions.add(productOption);
        productOption.assignProductOption(this);
    }

}
