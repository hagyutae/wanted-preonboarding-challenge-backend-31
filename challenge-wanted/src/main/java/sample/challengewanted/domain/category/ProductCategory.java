package sample.challengewanted.domain.category;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.domain.product.entity.Product;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_categories")
@Entity
public class ProductCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void associateProduct(Product product) {
        this.product = product;
    }
}
