package wanted.shop.category.domain.entity;

import jakarta.persistence.*;
import wanted.shop.brand.domain.entity.BrandId;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productCategories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
    @SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", allocationSize = 1)
    private Long id;

    public CategoryId getId() {
        return new CategoryId(id);
    }


    private String name;

    private String slug;

    private String description;
    private String imageUrl;
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();
}