package wanted.shop.brand.domain.entity;

import jakarta.persistence.*;
import wanted.shop.product.domain.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_id_seq")
    @SequenceGenerator(name = "brand_id_seq", sequenceName = "brand_id_seq", allocationSize = 1)
    private Long id;

    public BrandId getId() {
        return new BrandId(id);
    }

    private String name;

    private String slug;

    @Lob
    private String description;

    private String logoUrl;

    private String website;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
