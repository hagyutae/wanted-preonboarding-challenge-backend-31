package wanted.shop.product.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import wanted.shop.tag.domain.entity.Tag;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_tag_id_seq")
    @SequenceGenerator(name = "product_tag_id_seq", sequenceName = "product_tag_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Tag tag;
}
