package sample.challengewanted.domain.tag;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.domain.product.entity.Product;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_tags")
@Entity
public class ProductTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    private ProductTag(Tag tag, Product product) {
        this.tag = tag;
        this.product = product;
    }

    public static ProductTag create(Tag tag, Product product) {
        return ProductTag.builder()
                .tag(tag)
                .product(product)
                .build();
    }

    public void assignTag(Tag tag) {
        this.tag = tag;
    }

    public void assignProduct(Product product) {
        this.product = product;
    }
}
