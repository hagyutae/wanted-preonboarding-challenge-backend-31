package minseok.cqrschallenge.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import minseok.cqrschallenge.tag.entity.Tag;

@Entity
@Table(name = "product_tags")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @Builder
    public ProductTag(Product product, Tag tag) {
        this.product = product;
        this.tag = tag;
    }

    public void update(Product product, Tag tag) {
        this.product = product;
        this.tag = tag;
    }

    public void associateProduct(Product product) {
        this.product = product;
    }
}