package sample.challengewanted.domain.tag;

import jakarta.persistence.*;
import lombok.AccessLevel;
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

    public void assignTag(Tag tag) {
        this.tag = tag;
    }

    public void assignProduct(Product product) {
        this.product = product;
    }
}
