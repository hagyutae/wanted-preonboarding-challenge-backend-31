package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lkdcode.wanted.ecommerce.modules.tag.adapter.infrastructure.jpa.entity.TagJpaEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product_tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductTagJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagJpaEntity tag;

    @Builder
    public ProductTagJpaEntity(Long id, ProductJpaEntity product, TagJpaEntity tag) {
        this.id = id;
        this.product = product;
        this.tag = tag;
    }
}